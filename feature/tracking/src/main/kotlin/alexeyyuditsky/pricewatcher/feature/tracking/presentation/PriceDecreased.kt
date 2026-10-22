package alexeyyuditsky.pricewatcher.feature.tracking.presentation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.tracking.R
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

internal data class PriceDecreased(
    private val difference: String
) : PriceChangeUi {

    @Composable
    override fun priceColor(): Color = colorResource(R.color.price_decrease)

    @Composable
    override fun Show(style: TextStyle, modifier: Modifier) {
        val description = stringResource(R.string.price_decreased_description, difference)
        Text(
            text = stringResource(R.string.price_decreased, difference),
            modifier = modifier
                .testTag("priceDecreased")
                .semantics { contentDescription = description },
            style = style,
            color = priceColor(),
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(
                minFontSize = PRICE_CHANGE_MIN_FONT_SIZE,
                maxFontSize = style.fontSize
            )
        )
    }
}

@Preview(name = "Цена снизилась", showBackground = true)
@Composable
private fun PriceDecreasedPreview() {
    PricewatcherTheme {
        PriceDecreased("549 ₽").Show(style = MaterialTheme.typography.bodySmall)
    }
}