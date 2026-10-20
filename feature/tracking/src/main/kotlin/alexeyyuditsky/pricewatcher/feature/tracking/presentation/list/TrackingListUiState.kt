package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import androidx.compose.foundation.lazy.LazyListScope
import java.io.Serializable

internal sealed interface TrackingListUiState : Serializable {

    fun show(
        scope: LazyListScope,
        actions: TrackingListActions,
        onOpenProduct: (productId: String, title: String?) -> Unit
    )
}