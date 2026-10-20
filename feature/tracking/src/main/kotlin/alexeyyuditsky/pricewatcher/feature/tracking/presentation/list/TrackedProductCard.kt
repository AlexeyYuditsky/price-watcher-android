package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.tracking.R
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.TrackedProductUi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
internal fun TrackedProductCard(
    product: TrackedProductUi,
    onOpen: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        onClick = onOpen,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("trackedProduct_${product.id}")
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = product.title ?: stringResource(R.string.no_title),
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = product.price ?: stringResource(R.string.no_price),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = product.checkedAt?.let { stringResource(R.string.checked_at, it) }
                        ?: stringResource(R.string.not_checked),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            TextButton(onClick = onRemove, modifier = Modifier.testTag("removeProduct")) {
                Text(text = stringResource(R.string.remove_product))
            }
        }
    }
}

@Preview(name = "Карточка товара", showBackground = true)
@Composable
private fun TrackedProductCardPreview() {
    PricewatcherTheme {
        TrackedProductCard(
            product = TrackedProductUi(
                id = "1",
                title = "Рюкзак городской 20 л",
                imageUrl = null,
                price = "353,00 ₽",
                checkedAt = "20 окт., 16:30"
            ),
            onOpen = {},
            onRemove = {}
        )
    }
}