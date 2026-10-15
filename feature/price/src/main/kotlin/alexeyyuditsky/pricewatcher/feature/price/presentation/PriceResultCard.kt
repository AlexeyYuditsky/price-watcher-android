package alexeyyuditsky.pricewatcher.feature.price.presentation

import alexeyyuditsky.pricewatcher.feature.price.R
import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun PriceResultCard(price: ProductPriceUi?, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.price_label),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = price?.amount ?: stringResource(R.string.no_price),
                modifier = Modifier.testTag("productPrice"),
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = stringResource(R.string.fetched_at_label),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = price?.fetchedAt ?: stringResource(R.string.no_date),
                modifier = Modifier.testTag("priceFetchedAt"),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(name = "Карточка без цены", showBackground = true)
@Composable
private fun PriceResultCardEmptyPreview() {
    PricewatcherTheme {
        PriceResultCard(price = null)
    }
}

@Preview(name = "Карточка с ценой", showBackground = true)
@Composable
private fun PriceResultCardPreview() {
    PricewatcherTheme {
        PriceResultCard(
            price = ProductPriceUi(
                amount = "354,00 ₽",
                fetchedAt = "15 октября 2026, 15:00:00 GMT+3"
            )
        )
    }
}