package alexeyyuditsky.pricewatcher.feature.tracking.presentation.history

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.tracking.R
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.PriceIncreased
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.PricePeriodUi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun PricePeriodRow(period: PricePeriodUi) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // The price and its change keep their size; the period takes the rest of the line.
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = period.price,
                        style = MaterialTheme.typography.titleMedium,
                        color = period.priceChange.priceColor()
                    )
                    period.priceChange.Show(style = MaterialTheme.typography.bodySmall)
                }
                period.unitPrice?.let { unitPrice ->
                    Text(
                        text = stringResource(R.string.price_per_unit, unitPrice),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            // The period stays on one line and shrinks to fit next to the price.
            Text(
                text = period.period,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End,
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = MIN_PERIOD_FONT_SIZE,
                    maxFontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            )
        }
    }
}

private val MIN_PERIOD_FONT_SIZE = 8.sp

@Preview(name = "Период цены", showBackground = true)
@Composable
private fun PricePeriodRowPreview() {
    PricewatcherTheme {
        PricePeriodRow(
            period = PricePeriodUi(
                price = "1 527,00 ₽",
                unitPrice = "169,67 ₽",
                period = "19 окт., 10:00 — 20 окт., 16:30",
                priceChange = PriceIncreased("37 ₽")
            )
        )
    }
}