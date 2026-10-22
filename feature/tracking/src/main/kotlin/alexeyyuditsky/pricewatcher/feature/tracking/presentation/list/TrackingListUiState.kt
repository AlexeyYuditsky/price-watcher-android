package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationUiState
import androidx.compose.foundation.lazy.LazyListScope
import java.io.Serializable

internal sealed interface TrackingListUiState : Serializable {

    fun show(
        scope: LazyListScope,
        actions: TrackingListActions,
        operation: OperationUiState,
        highlightedProductId: String?,
        onOpenProduct: (productId: String, title: String?, url: String) -> Unit
    )

    fun productPosition(productId: String): Int? = null
}