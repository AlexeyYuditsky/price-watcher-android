package alexeyyuditsky.pricewatcher.feature.price.presentation

import alexeyyuditsky.pricewatcher.feature.price.R
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import java.io.Serializable

internal sealed interface PriceUiState : Serializable {

    @Composable
    fun Show(actions: PriceActions)

    fun allowsInput(): Boolean = true

    fun isInputError(): Boolean = false

    @StringRes
    fun fetchButtonText(): Int = R.string.fetch_price
}