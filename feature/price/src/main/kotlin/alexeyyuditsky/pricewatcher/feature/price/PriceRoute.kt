package alexeyyuditsky.pricewatcher.feature.price

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.price.presentation.PricePreviewActions
import alexeyyuditsky.pricewatcher.feature.price.presentation.PriceScreen
import alexeyyuditsky.pricewatcher.feature.price.presentation.PriceInitial
import alexeyyuditsky.pricewatcher.feature.price.presentation.PriceViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PriceRoute(
    sharedText: String?,
    onSharedTextConsumed: () -> Unit,
    onBack: () -> Unit
) {
    val viewModel: PriceViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val url by viewModel.url.collectAsStateWithLifecycle()
    LaunchedEffect(sharedText) {
        sharedText?.let { text ->
            viewModel.receiveSharedText(text)
            onSharedTextConsumed()
        }
    }
    PriceScreen(state = state, url = url, actions = viewModel, onBack = onBack)
}

@Preview(name = "Экран получения цены", showBackground = true)
@Composable
private fun PriceRoutePreview() {
    PricewatcherTheme {
        PriceScreen(state = PriceInitial, url = "", actions = PricePreviewActions)
    }
}