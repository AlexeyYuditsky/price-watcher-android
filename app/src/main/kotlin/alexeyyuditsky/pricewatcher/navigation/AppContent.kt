package alexeyyuditsky.pricewatcher.navigation

import alexeyyuditsky.pricewatcher.core.auth.domain.AuthorizationStatus
import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.auth.AuthRoute
import alexeyyuditsky.pricewatcher.feature.services.ServicesScreen
import alexeyyuditsky.pricewatcher.feature.tracking.HistoryRoute
import alexeyyuditsky.pricewatcher.feature.tracking.TrackingRoute
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun AppContent(
    authorizationStatus: AuthorizationStatus,
    sharedText: String?,
    onSharedTextConsumed: () -> Unit,
    onLogout: () -> Unit
) {
    when (authorizationStatus) {
        AuthorizationStatus.CHECKING -> return
        AuthorizationStatus.UNAUTHORIZED -> {
            AuthRoute()
            return
        }
        AuthorizationStatus.AUTHORIZED -> Unit
    }
    var destination by rememberSaveable { mutableStateOf(AppDestination.SERVICES) }
    var historyProductId by rememberSaveable { mutableStateOf("") }
    var historyTitle by rememberSaveable { mutableStateOf<String?>(null) }
    LaunchedEffect(sharedText) {
        if (sharedText != null) destination = AppDestination.TRACKING
    }
    BackHandler(enabled = destination != AppDestination.SERVICES) {
        destination = if (destination == AppDestination.HISTORY) {
            AppDestination.TRACKING
        } else {
            AppDestination.SERVICES
        }
    }
    when (destination) {
        AppDestination.SERVICES -> ServicesScreen(
            onOzonClick = { destination = AppDestination.TRACKING },
            onLogout = onLogout
        )
        AppDestination.TRACKING -> TrackingRoute(
            sharedText = sharedText,
            onSharedTextConsumed = onSharedTextConsumed,
            onBack = { destination = AppDestination.SERVICES },
            onOpenProduct = { productId, title ->
                historyProductId = productId
                historyTitle = title
                destination = AppDestination.HISTORY
            }
        )
        AppDestination.HISTORY -> HistoryRoute(
            productId = historyProductId,
            title = historyTitle,
            onBack = { destination = AppDestination.TRACKING }
        )
    }
}

@Preview(name = "Главный экран приложения", showBackground = true)
@Composable
private fun AppContentPreview() {
    PricewatcherTheme {
        ServicesScreen(onOzonClick = {}, onLogout = {})
    }
}