package alexeyyuditsky.pricewatcher.feature.tracking

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.history.HistoryContent
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.history.HistoryPreviewActions
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.history.HistoryScreen
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.history.HistoryViewModel
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationIdle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HistoryRoute(
    productId: String,
    title: String?,
    url: String,
    onBack: () -> Unit
) {
    val viewModel: HistoryViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val operation by viewModel.operation.collectAsStateWithLifecycle()
    LaunchedEffect(productId) {
        viewModel.open(productId)
    }
    HistoryScreen(
        title = title,
        url = url,
        state = state,
        operation = operation,
        actions = viewModel,
        onBack = onBack
    )
}

@Preview(name = "Экран истории", showBackground = true)
@Composable
private fun HistoryRoutePreview() {
    PricewatcherTheme {
        HistoryScreen(
            title = "Рюкзак городской",
            url = "https://www.ozon.ru/product/5677075076/",
            state = HistoryContent(emptyList()),
            operation = OperationIdle,
            actions = HistoryPreviewActions,
            onBack = {}
        )
    }
}