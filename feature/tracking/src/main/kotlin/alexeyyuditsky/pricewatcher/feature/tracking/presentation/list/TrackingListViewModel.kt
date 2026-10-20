package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import alexeyyuditsky.pricewatcher.core.common.RunAsync
import alexeyyuditsky.pricewatcher.core.products.domain.GetTrackedProductsUseCase
import alexeyyuditsky.pricewatcher.core.products.domain.RemoveProductUseCase
import alexeyyuditsky.pricewatcher.core.products.domain.TrackProductUseCase
import alexeyyuditsky.pricewatcher.core.products.domain.TrackingResult
import alexeyyuditsky.pricewatcher.core.products.domain.extractOzonProductUrl
import alexeyyuditsky.pricewatcher.feature.tracking.R
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationFailed
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationIdle
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationResultUiMapper
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationRunning
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationUiState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
internal class TrackingListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getProducts: GetTrackedProductsUseCase,
    private val trackProduct: TrackProductUseCase,
    private val removeProduct: RemoveProductUseCase,
    private val runAsync: RunAsync,
    private val listMapper: TrackingListResultUiMapper,
    private val operationMapper: OperationResultUiMapper
) : ViewModel(), TrackingListActions {

    val state: StateFlow<TrackingListUiState> =
        savedStateHandle.getStateFlow(STATE_KEY, TrackingListLoading)
    val operation: StateFlow<OperationUiState> =
        savedStateHandle.getStateFlow(OPERATION_KEY, OperationIdle)
    val url: StateFlow<String> = savedStateHandle.getStateFlow(URL_KEY, "")

    init {
        if (!operation.value.allowsInput()) updateOperation(OperationIdle)
        reload()
    }

    override fun changeUrl(url: String) {
        if (!operation.value.allowsInput()) return
        savedStateHandle[URL_KEY] = url
        updateOperation(OperationIdle)
    }

    override fun pasteFromClipboard(text: String) {
        if (!operation.value.allowsInput()) return
        if (text.isBlank()) {
            updateOperation(OperationFailed(R.string.error_clipboard_empty))
            return
        }
        val extractedUrl = runCatching { extractOzonProductUrl(text) }.getOrNull()
        if (extractedUrl == null) {
            updateOperation(OperationFailed(R.string.error_clipboard_url))
        } else {
            changeUrl(extractedUrl)
        }
    }

    // While another operation runs, keep the shared link in the field so the user can add it afterwards.
    fun receiveSharedText(text: String) {
        savedStateHandle[URL_KEY] = text
        if (operation.value.allowsInput()) addProduct()
    }

    override fun addProduct() {
        val input = url.value
        if (input.isBlank()) return
        runOperation(clearsUrl = true) { trackProduct(input) }
    }

    override fun removeProduct(productId: String) {
        runOperation(clearsUrl = false) { removeProduct.invoke(productId) }
    }

    override fun reload() {
        runAsync.runAsync(
            scope = viewModelScope,
            background = { getProducts().map(listMapper) }
        ) { result ->
            savedStateHandle[STATE_KEY] = result.getOrElse { listMapper.mapInvalidResponse() }
        }
    }

    private fun runOperation(clearsUrl: Boolean, request: suspend () -> TrackingResult) {
        if (!operation.value.allowsInput()) return
        updateOperation(OperationRunning)
        runAsync.runAsync(
            scope = viewModelScope,
            background = { request().map(operationMapper) }
        ) { result ->
            val next = result.getOrElse { operationMapper.mapInvalidResponse() }
            if (next.reloadsData()) {
                if (clearsUrl) savedStateHandle[URL_KEY] = ""
                reload()
            }
            updateOperation(next)
        }
    }

    private fun updateOperation(state: OperationUiState) {
        savedStateHandle[OPERATION_KEY] = state
    }

    companion object {
        private const val STATE_KEY = "tracking_list_state"
        private const val OPERATION_KEY = "tracking_list_operation"
        private const val URL_KEY = "tracking_url"
    }
}