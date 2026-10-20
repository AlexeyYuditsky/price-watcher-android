package alexeyyuditsky.pricewatcher.feature.tracking.presentation.history

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.tracking.R
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.PricePeriodUi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun PricePeriodRow(period: PricePeriodUi) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = period.price,
                style = MaterialTheme.typography.titleLarge
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(text = period.period, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = stringResource(R.string.period_checks, period.checks),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(name = "Период цены", showBackground = true)
@Composable
private fun PricePeriodRowPreview() {
    PricewatcherTheme {
        PricePeriodRow(
            period = PricePeriodUi(
                price = "353,00 ₽",
                period = "19 окт., 10:00 — 20 окт., 16:30",
                checks = 61
            )
        )
    }
}