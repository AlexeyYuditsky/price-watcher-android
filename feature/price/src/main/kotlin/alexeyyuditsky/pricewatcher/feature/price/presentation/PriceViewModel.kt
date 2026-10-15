package alexeyyuditsky.pricewatcher.feature.price.presentation

import alexeyyuditsky.pricewatcher.core.common.RunAsync
import alexeyyuditsky.pricewatcher.core.products.domain.GetProductPriceUseCase
import alexeyyuditsky.pricewatcher.core.products.domain.extractOzonProductUrl
import alexeyyuditsky.pricewatcher.feature.price.R
import alexeyyuditsky.pricewatcher.feature.price.presentation.mapper.PriceResultUiMapper
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
internal class PriceViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getProductPrice: GetProductPriceUseCase,
    private val runAsync: RunAsync,
    private val resultMapper: PriceResultUiMapper
) : ViewModel(), PriceActions {

    val url: StateFlow<String> = savedStateHandle.getStateFlow(URL_KEY, "")
    val state: StateFlow<PriceUiState> = savedStateHandle.getStateFlow(STATE_KEY, PriceInitial)

    init {
        val restored = state.value
        if (restored is PriceLoading) {
            update(PriceInitial)
            restored.pendingSharedText?.let(::receiveSharedText)
        }
    }

    override fun changeUrl(url: String) {
        if (!state.value.allowsInput()) return
        savedStateHandle[URL_KEY] = url
        update(PriceInitial)
    }

    override fun pasteFromClipboard(text: String) {
        if (!state.value.allowsInput()) return
        if (text.isBlank()) {
            update(PriceError(R.string.error_clipboard_empty))
            return
        }
        val extractedUrl = runCatching { extractOzonProductUrl(text) }.getOrNull()
        if (extractedUrl == null) {
            update(PriceError(R.string.error_clipboard_url))
        } else {
            changeUrl(extractedUrl)
        }
    }

    fun receiveSharedText(text: String) {
        val current = state.value
        if (current is PriceLoading) {
            update(current.copy(pendingSharedText = text))
            return
        }
        val extractedUrl = runCatching { extractOzonProductUrl(text) }.getOrNull()
        savedStateHandle[URL_KEY] = extractedUrl.orEmpty()
        update(if (extractedUrl == null) PriceError(R.string.error_shared_url) else PriceInitial)
    }

    override fun fetchPrice() {
        val input = url.value
        if (!state.value.allowsInput() || input.isBlank()) return
        update(PriceLoading())
        runAsync.runAsync(
            scope = viewModelScope,
            background = { getProductPrice(input).map(resultMapper) }
        ) { result ->
            val pendingSharedText = (state.value as? PriceLoading)?.pendingSharedText
            if (pendingSharedText != null) {
                update(PriceInitial)
                receiveSharedText(pendingSharedText)
            } else {
                update(result.getOrElse { resultMapper.mapInvalidResponse() })
            }
        }
    }

    private fun update(state: PriceUiState) {
        savedStateHandle[STATE_KEY] = state
    }

    companion object {
        private const val STATE_KEY = "price_ui_state"
        private const val URL_KEY = "price_url"
    }
}