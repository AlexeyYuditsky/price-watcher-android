package alexeyyuditsky.pricewatcher.navigation

import alexeyyuditsky.pricewatcher.core.auth.domain.AuthorizationStatus
import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.auth.AuthRoute
import alexeyyuditsky.pricewatcher.feature.services.ServicesScreen
import alexeyyuditsky.pricewatcher.feature.tracking.HistoryRoute
import alexeyyuditsky.pricewatcher.feature.tracking.TrackingRoute
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

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
    val backStack = rememberSaveable(saver = BackStackSaver) {
        mutableStateListOf<AppDestination>(ServicesDestination)
    }
    val goBack = {
        if (backStack.size > 1) backStack.removeAt(backStack.lastIndex)
    }
    LaunchedEffect(sharedText) {
        if (sharedText == null) return@LaunchedEffect
        if (TrackingDestination in backStack) {
            while (backStack.last() != TrackingDestination) backStack.removeAt(backStack.lastIndex)
        } else {
            backStack.add(TrackingDestination)
        }
    }
    // Each entry gets its own ViewModelStore, so leaving a screen with Back cancels its work.
    NavDisplay(
        backStack = backStack,
        onBack = goBack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<ServicesDestination> {
                ServicesScreen(
                    onOzonClick = { backStack.add(TrackingDestination) },
                    onLogout = onLogout
                )
            }
            entry<TrackingDestination> {
                TrackingRoute(
                    sharedText = sharedText,
                    onSharedTextConsumed = onSharedTextConsumed,
                    onBack = goBack,
                    onOpenProduct = { productId, title, url ->
                        backStack.add(HistoryDestination(productId, title, url))
                    }
                )
            }
            entry<HistoryDestination> { destination ->
                HistoryRoute(
                    productId = destination.productId,
                    title = destination.title,
                    url = destination.url,
                    onBack = goBack
                )
            }
        }
    )
}

private val BackStackSaver = listSaver<SnapshotStateList<AppDestination>, AppDestination>(
    save = { it.toList() },
    restore = { it.toMutableStateList() }
)

@Preview(name = "Главный экран приложения", showBackground = true)
@Composable
private fun AppContentPreview() {
    PricewatcherTheme {
        ServicesScreen(onOzonClick = {}, onLogout = {})
    }
}