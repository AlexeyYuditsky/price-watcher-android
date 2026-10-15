package alexeyyuditsky.pricewatcher.feature.price.presentation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.price.R
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

internal data class PriceError(
    @field:StringRes private val message: Int
) : PriceUiState {

    override fun isInputError(): Boolean = message == R.string.error_invalid_url

    @Composable
    override fun Show(actions: PriceActions) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Text(
                text = stringResource(message),
                modifier = Modifier.testTag("priceError"),
                color = MaterialTheme.colorScheme.error
            )
            PriceResultCard(price = null)
        }
    }
}

@Preview(name = "Состояние ошибки", showBackground = true)
@Composable
private fun PriceErrorPreview() {
    PricewatcherTheme {
        PriceError(R.string.error_network).Show(PricePreviewActions)
    }
}