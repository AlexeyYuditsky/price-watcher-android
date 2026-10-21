package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import androidx.compose.runtime.Composable

internal data object RemovalHidden : RemovalUiState {

    @Composable
    override fun Show(actions: TrackingListActions) = Unit

    override fun confirm(remove: (productId: String) -> Unit) = Unit

    private fun readResolve(): Any = RemovalHidden
}