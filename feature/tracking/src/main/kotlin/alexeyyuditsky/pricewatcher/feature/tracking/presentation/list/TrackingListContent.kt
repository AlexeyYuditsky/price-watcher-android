package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import alexeyyuditsky.pricewatcher.feature.tracking.R
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.TrackedProductUi
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationUiState
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
        operation: OperationUiState,
        highlightedProductId: String?,
        onOpenProduct: (productId: String, title: String?, url: String) -> Unit
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
                highlighted = product.id == highlightedProductId,
                onOpen = { onOpenProduct(product.id, product.title, product.url) },
                onRemove = { actions.requestRemoval(product.id, product.title) },
                onHighlightShown = actions::highlightShown
            )
        }
    }

    override fun productPosition(productId: String): Int? =
        products.indexOfFirst { it.id == productId }.takeIf { it >= 0 }
}