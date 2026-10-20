package alexeyyuditsky.pricewatcher.feature.auth.presentation

import androidx.compose.runtime.Composable

internal data object AuthSuccess : AuthUiState {

    override fun keepsPassword(): Boolean = false

    @Composable
    override fun Show() = Unit

    private fun readResolve(): Any = AuthSuccess
}