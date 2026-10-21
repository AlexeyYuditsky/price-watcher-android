package alexeyyuditsky.pricewatcher.feature.tracking

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.list.RemovalHidden
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.list.TrackingListContent
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.list.TrackingListPreviewActions
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.list.TrackingListScreen
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.list.TrackingListViewModel
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationIdle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TrackingRoute(
    sharedText: String?,
    onSharedTextConsumed: () -> Unit,
    onBack: () -> Unit,
    onOpenProduct: (productId: String, title: String?, url: String) -> Unit
) {
    val viewModel: TrackingListViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val operation by viewModel.operation.collectAsStateWithLifecycle()
    val removal by viewModel.removal.collectAsStateWithLifecycle()
    val url by viewModel.url.collectAsStateWithLifecycle()
    val highlightedProductId by viewModel.highlightedProductId.collectAsStateWithLifecycle()
    // Reloading on every start refreshes prices and check times after the product screen
    // and after the app returns from the background.
    LifecycleStartEffect(viewModel) {
        viewModel.reload()
        onStopOrDispose { }
    }
    LaunchedEffect(sharedText) {
        sharedText?.let { text ->
            viewModel.receiveSharedText(text)
            onSharedTextConsumed()
        }
    }
    TrackingListScreen(
        state = state,
        operation = operation,
        removal = removal,
        url = url,
        highlightedProductId = highlightedProductId,
        actions = viewModel,
        onBack = onBack,
        onOpenProduct = onOpenProduct
    )
}

@Preview(name = "Экран отслеживания", showBackground = true)
@Composable
private fun TrackingRoutePreview() {
    PricewatcherTheme {
        TrackingListScreen(
            state = TrackingListContent(emptyList()),
            operation = OperationIdle,
            removal = RemovalHidden,
            url = "",
            highlightedProductId = null,
            actions = TrackingListPreviewActions,
            onBack = {},
            onOpenProduct = { _, _, _ -> }
        )
    }
}