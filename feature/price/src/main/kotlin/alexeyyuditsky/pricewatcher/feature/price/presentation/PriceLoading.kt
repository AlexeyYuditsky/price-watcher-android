package alexeyyuditsky.pricewatcher.feature.price.presentation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.price.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

internal data class PriceLoading(
    val pendingSharedText: String? = null
) : PriceUiState {

    override fun allowsInput(): Boolean = false

    override fun fetchButtonText(): Int = R.string.fetching_price

    @Composable
    override fun Show(actions: PriceActions) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            CircularProgressIndicator(modifier = Modifier.testTag("priceLoading"))
            if (pendingSharedText != null) {
                Text(text = stringResource(R.string.shared_url_pending))
            }
            PriceResultCard(price = null)
        }
    }
}

@Preview(name = "Состояние загрузки", showBackground = true)
@Composable
private fun PriceLoadingPreview() {
    PricewatcherTheme {
        PriceLoading().Show(PricePreviewActions)
    }
}