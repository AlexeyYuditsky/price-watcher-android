package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import alexeyyuditsky.pricewatcher.feature.tracking.R
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.TrackedProductUi
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource

internal data class TrackingListContent(
    private val products: List<TrackedProductUi>
) : TrackingListUiState {

    override fun show(
        scope: LazyListScope,
        actions: TrackingListActions,
        onOpenProduct: (productId: String, title: String?) -> Unit
    ) {
        if (products.isEmpty()) {
            scope.item(key = "empty") {
                Text(
                    text = stringResource(R.string.tracking_empty),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            return
        }
        scope.items(items = products, key = { it.id }) { product ->
            TrackedProductCard(
                product = product,
                onOpen = { onOpenProduct(product.id, product.title) },
                onRemove = { actions.removeProduct(product.id) }
            )
        }
    }
}