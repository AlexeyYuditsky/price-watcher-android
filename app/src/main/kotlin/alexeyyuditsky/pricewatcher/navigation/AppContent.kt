package alexeyyuditsky.pricewatcher.navigation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.price.PriceRoute
import alexeyyuditsky.pricewatcher.feature.services.ServicesScreen
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun AppContent(sharedText: String?, onSharedTextConsumed: () -> Unit) {
    var destination by rememberSaveable { mutableStateOf(AppDestination.SERVICES) }
    LaunchedEffect(sharedText) {
        if (sharedText != null) destination = AppDestination.OZON
    }
    BackHandler(enabled = destination == AppDestination.OZON) {
        destination = AppDestination.SERVICES
    }
    when (destination) {
        AppDestination.SERVICES -> ServicesScreen(
            onOzonClick = { destination = AppDestination.OZON }
        )
        AppDestination.OZON -> PriceRoute(
            sharedText = sharedText,
            onSharedTextConsumed = onSharedTextConsumed,
            onBack = { destination = AppDestination.SERVICES }
        )
    }
}

@Preview(name = "Главный экран приложения", showBackground = true)
@Composable
private fun AppContentPreview() {
    PricewatcherTheme {
        ServicesScreen(onOzonClick = {})
    }
}