package alexeyyuditsky.pricewatcher.ui.price

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import alexeyyuditsky.pricewatcher.R
import alexeyyuditsky.pricewatcher.domain.GetProductPriceUseCase
import alexeyyuditsky.pricewatcher.domain.ProductPrice
import alexeyyuditsky.pricewatcher.domain.extractOzonProductUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

data class PriceUiState(
    val url: String = "",
    val isLoading: Boolean = false,
    val price: ProductPrice? = null,
    @field:StringRes val error: Int? = null
)

@HiltViewModel
class PriceViewModel @Inject constructor(private val getProductPrice: GetProductPriceUseCase) : ViewModel() {
    private val mutableState = MutableStateFlow(PriceUiState())
    val state = mutableState.asStateFlow()

    fun onUrlChanged(url: String) {
        if (state.value.isLoading) return
        mutableState.update { it.copy(url = url, price = null, error = null) }
    }

    fun pasteFromClipboard(text: String) {
        if (state.value.isLoading) return
        if (text.isBlank()) {
            mutableState.update { it.copy(error = R.string.error_clipboard_empty) }
            return
        }
        val url = runCatching { extractOzonProductUrl(text) }.getOrNull()
        if (url == null) {
            mutableState.update { it.copy(error = R.string.error_clipboard_url) }
        } else {
            onUrlChanged(url)
        }
    }

    fun fetchPrice() {
        val current = state.value
        if (current.isLoading || current.url.isBlank()) return
        mutableState.update { it.copy(isLoading = true, price = null, error = null) }
        viewModelScope.launch {
            try {
                val price = getProductPrice(current.url)
                mutableState.update { it.copy(isLoading = false, price = price) }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                val message = when (error) {
                    is HttpException -> when (error.code()) {
                        400 -> R.string.error_invalid_url
                        502 -> R.string.error_product_unavailable
                        else -> R.string.error_server
                    }
                    is SocketTimeoutException -> R.string.error_timeout
                    is IOException -> R.string.error_network
                    is IllegalArgumentException -> R.string.error_invalid_url
                    else -> R.string.error_response
                }
                mutableState.update { it.copy(isLoading = false, error = message) }
            }
        }
    }
}