package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.tracking.R
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.TrackedProductUi
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.openInOzon
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
internal fun TrackedProductCard(
    product: TrackedProductUi,
    highlighted: Boolean,
    onOpen: () -> Unit,
    onRemove: () -> Unit,
    onHighlightShown: () -> Unit
) {
    val context = LocalContext.current
    val highlight = remember { Animatable(0f) }
    // A just added product flashes once, then the highlight is reported as shown.
    LaunchedEffect(highlighted) {
        if (!highlighted) return@LaunchedEffect
        highlight.snapTo(1f)
        highlight.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 1200, delayMillis = 600)
        )
        onHighlightShown()
    }
    Card(
        onClick = onOpen,
        colors = CardDefaults.cardColors(
            containerColor = lerp(
                start = CardDefaults.cardColors().containerColor,
                stop = MaterialTheme.colorScheme.primaryContainer,
                fraction = highlight.value
            )
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("trackedProduct_${product.id}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // The whole photo is fitted; Ozon photos have a white background, so the margins blend in.
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(112.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(colorResource(R.color.product_image_background))
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = product.title ?: stringResource(R.string.no_title),
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 3,
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
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
            ) {
                TextButton(
                    onClick = { context.openInOzon(product.url) },
                    modifier = Modifier.testTag("openProductInOzon")
                ) {
                    Text(text = stringResource(R.string.open_in_ozon))
                }
                TextButton(onClick = onRemove, modifier = Modifier.testTag("removeProduct")) {
                    Text(text = stringResource(R.string.remove_product))
                }
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
                url = "https://www.ozon.ru/product/5677075076/",
                title = "Рюкзак городской 20 л",
                imageUrl = null,
                price = "353,00 ₽",
                checkedAt = "20 окт., 16:30"
            ),
            highlighted = false,
            onOpen = {},
            onRemove = {},
            onHighlightShown = {}
        )
    }
}