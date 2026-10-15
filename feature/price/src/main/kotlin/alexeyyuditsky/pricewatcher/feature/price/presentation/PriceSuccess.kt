package alexeyyuditsky.pricewatcher.feature.price.presentation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

internal data class PriceSuccess(
    private val price: ProductPriceUi
) : PriceUiState {

    @Composable
    override fun Show(actions: PriceActions) {
        PriceResultCard(price = price)
    }
}

@Preview(name = "Успешное состояние", showBackground = true)
@Composable
private fun PriceSuccessPreview() {
    PricewatcherTheme {
        PriceSuccess(
            ProductPriceUi(amount = "354,00 ₽", fetchedAt = "15 октября 2026, 15:00:00 GMT+3")
        ).Show(PricePreviewActions)
    }
}