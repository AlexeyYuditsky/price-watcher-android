package alexeyyuditsky.pricewatcher.navigation

import alexeyyuditsky.pricewatcher.core.auth.domain.AuthorizationStatus
import alexeyyuditsky.pricewatcher.core.auth.domain.LogoutUseCase
import alexeyyuditsky.pricewatcher.core.auth.domain.ObserveAuthorizationUseCase
import alexeyyuditsky.pricewatcher.core.common.RunAsync
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
internal class AppViewModel @Inject constructor(
    observeAuthorization: ObserveAuthorizationUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val runAsync: RunAsync
) : ViewModel() {

    val authorizationStatus: StateFlow<AuthorizationStatus> = observeAuthorization()

    fun logout() {
        if (authorizationStatus.value != AuthorizationStatus.AUTHORIZED) return
        runAsync.runAsync(
            scope = viewModelScope,
            background = { logoutUseCase() },
            ui = {}
        )
    }
}