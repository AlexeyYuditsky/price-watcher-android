package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

internal data object TrackingListLoading : TrackingListUiState {

    override fun show(
        scope: LazyListScope,
        actions: TrackingListActions,
        highlightedProductId: String?,
        onOpenProduct: (productId: String, title: String?, url: String) -> Unit
    ) {
        scope.item(key = "loading") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.testTag("trackingLoading"))
            }
        }
    }

    private fun readResolve(): Any = TrackingListLoading
}