package alexeyyuditsky.pricewatcher.feature.tracking.presentation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.tracking.R
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

// The price and its change are one text: they share a baseline and shrink together, keeping their
// proportions, just enough for the line to fit.
@Composable
internal fun PriceWithChange(
    price: String,
    priceChange: PriceChangeUi,
    modifier: Modifier = Modifier
) {
    val priceStyle = MaterialTheme.typography.titleMedium
    val changeFontSize = (MaterialTheme.typography.bodySmall.fontSize.value / priceStyle.fontSize.value).em
    val change = priceChange.change()
    val description = priceChange.description()
        ?.let { stringResource(R.string.price_with_change_description, price, it) }
    Text(
        text = buildAnnotatedString {
            append(price)
            if (change != null) {
                append(' ')
                withStyle(SpanStyle(fontSize = changeFontSize)) { append(change) }
            }
        },
        modifier = modifier.semantics {
            if (description != null) contentDescription = description
        },
        style = priceStyle,
        color = priceChange.priceColor(),
        maxLines = 1,
        autoSize = TextAutoSize.StepBased(
            minFontSize = MIN_PRICE_FONT_SIZE,
            maxFontSize = priceStyle.fontSize
        )
    )
}

private val MIN_PRICE_FONT_SIZE = 1.sp

@Preview(name = "Цена снизилась", showBackground = true)
@Composable
private fun PriceWithChangeDecreasedPreview() {
    PricewatcherTheme {
        PriceWithChange(price = "1 527,00 ₽", priceChange = PriceDecreased("49 ₽"))
    }
}

@Preview(name = "Цена выросла", showBackground = true)
@Composable
private fun PriceWithChangeIncreasedPreview() {
    PricewatcherTheme {
        PriceWithChange(price = "54 990,00 ₽", priceChange = PriceIncreased("1 200 ₽"))
    }
}

@Preview(name = "Цена в узком месте", showBackground = true, widthDp = 90)
@Composable
private fun PriceWithChangeNarrowPreview() {
    PricewatcherTheme {
        PriceWithChange(price = "1 527,00 ₽", priceChange = PriceDecreased("49 ₽"))
    }
}