package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import androidx.compose.runtime.Composable
import java.io.Serializable

internal sealed interface RemovalUiState : Serializable {

    @Composable
    fun Show(actions: TrackingListActions)

    fun confirm(remove: (productId: String) -> Unit)
}