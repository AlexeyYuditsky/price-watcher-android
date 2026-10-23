package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.tracking.R
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.PriceDecreased
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.PriceWithChange
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.TrackedProductUi
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.openInOzon
import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.annotation.ExperimentalCoilApi
import coil3.asImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.compose.rememberAsyncImagePainter

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
    // A just added product blinks a few times, then the highlight is reported as shown.
    LaunchedEffect(highlighted) {
        if (!highlighted) return@LaunchedEffect
        repeat(HIGHLIGHT_BLINKS) {
            highlight.animateTo(targetValue = 1f, animationSpec = tween(durationMillis = 250))
            highlight.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = 450))
        }
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
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary.copy(alpha = highlight.value)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("trackedProduct_${product.id}")
    ) {
        val photo = rememberAsyncImagePainter(product.imageUrl)
        val photoState by photo.state.collectAsState()
        // Ozon photos come in different proportions (3:4, 2:3...), so the frame takes the loaded photo's
        // proportions and shows it whole; until it loads, the most common 3:4 is assumed.
        val photoAspectRatio = photoState.painter?.intrinsicSize
            ?.takeIf { it.isSpecified && it.width > 0f && it.height > 0f }
            ?.let { (it.width / it.height).coerceIn(MIN_PHOTO_ASPECT_RATIO, MAX_PHOTO_ASPECT_RATIO) }
            ?: OZON_PHOTO_ASPECT_RATIO
        val photoHeight = PHOTO_WIDTH / photoAspectRatio
        Row(
            horizontalArrangement = Arrangement.spacedBy(PHOTO_CONTENT_GAP),
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            // When the text column grows taller than the photo, the white frame stretches to the card
            // bottom and the photo stays whole in its center, blending with the white Ozon background.
            Image(
                painter = photo,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = PHOTO_PADDING, top = PHOTO_PADDING, bottom = PHOTO_PADDING)
                    .width(PHOTO_WIDTH)
                    .fillMaxHeight()
                    .clip(MaterialTheme.shapes.small)
                    .background(colorResource(R.color.product_image_background))
            )
            // The text starts at the top of the photo and the buttons end at its bottom; with a larger
            // system font the column grows instead of clipping.
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        top = PHOTO_PADDING,
                        end = PHOTO_PADDING,
                        bottom = PHOTO_PADDING
                    )
                    .heightIn(min = photoHeight),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = product.title ?: stringResource(R.string.no_title),
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    PriceWithChange(
                        price = product.price ?: stringResource(R.string.no_price),
                        priceChange = product.priceChange
                    )
                    product.unitPrice?.let { unitPrice ->
                        DetailText(text = stringResource(R.string.price_per_unit, unitPrice))
                    }
                    DetailText(
                        text = product.checkedAt?.let { stringResource(R.string.checked_at, it) }
                            ?: stringResource(R.string.not_checked)
                    )
                }
                // Buttons stay on one line; each label shrinks if a translation does not fit its half.
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    CardButton(
                        text = stringResource(R.string.open_in_ozon_short),
                        onClick = { context.openInOzon(product.url) },
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .testTag("openProductInOzon")
                    )
                    CardButton(
                        text = stringResource(R.string.remove_product),
                        onClick = onRemove,
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .testTag("removeProduct")
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1,
        autoSize = TextAutoSize.StepBased(
            minFontSize = MIN_FONT_SIZE,
            maxFontSize = MaterialTheme.typography.bodySmall.fontSize
        )
    )
}

@Composable
private fun CardButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 8.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(
                minFontSize = MIN_FONT_SIZE,
                maxFontSize = MaterialTheme.typography.labelLarge.fontSize
            )
        )
    }
}

private const val HIGHLIGHT_BLINKS = 2
private const val OZON_PHOTO_ASPECT_RATIO = 3f / 4f

// Very tall or wide photos are fitted into a frame no taller than 1:2 and no wider than a square.
private const val MIN_PHOTO_ASPECT_RATIO = 1f / 2f
private const val MAX_PHOTO_ASPECT_RATIO = 1f
// Single-line texts shrink down to this size so the whole card fits even very narrow screens.
private val MIN_FONT_SIZE = 1.sp
private val PHOTO_WIDTH = 120.dp
private val PHOTO_PADDING = 12.dp
private val PHOTO_CONTENT_GAP = 16.dp

@OptIn(ExperimentalCoilApi::class)
@Preview(name = "Карточка товара", showBackground = true, locale = "ru")
@Preview(
    name = "Карточка товара — тёмная тема",
    showBackground = true,
    locale = "ru",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun TrackedProductCardPreview() {
    val context = LocalContext.current
    // Preview has no network: Coil gets the bundled sample photo instead of loading imageUrl.
    val previewImage = AsyncImagePreviewHandler {
        checkNotNull(context.getDrawable(R.drawable.preview_tracked_product)).asImage()
    }
    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewImage) {
        PricewatcherTheme {
            TrackedProductCard(
                product = TrackedProductUi(
                    id = "1",
                    url = "https://www.ozon.ru/product/5677075076/",
                    title = "Энергетический напиток Flash Up Energy Original, 24 шт. х 0,45 л",
                    imageUrl = "https://ir.ozone.ru/s3/multimedia-preview/wc500/preview.jpg",
                    price = "1 527,00 ₽",
                    priceChange = PriceDecreased("49 ₽"),
                    unitPrice = "63,63 ₽",
                    checkedAt = "20 окт., 16:30"
                ),
                highlighted = false,
                onOpen = {},
                onRemove = {},
                onHighlightShown = {}
            )
        }
    }
}