package alexeyyuditsky.pricewatcher.feature.price.presentation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

internal data object PriceInitial : PriceUiState {

    @Composable
    override fun Show(actions: PriceActions) {
        PriceResultCard(price = null)
    }

    private fun readResolve(): Any = PriceInitial
}

@Preview(name = "Начальное состояние", showBackground = true)
@Composable
private fun PriceInitialPreview() {
    PricewatcherTheme {
        PriceInitial.Show(PricePreviewActions)
    }
}