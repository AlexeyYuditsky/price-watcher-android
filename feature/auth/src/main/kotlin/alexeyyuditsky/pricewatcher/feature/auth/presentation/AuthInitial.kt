package alexeyyuditsky.pricewatcher.feature.auth.presentation

import androidx.compose.runtime.Composable

internal data object AuthInitial : AuthUiState {

    @Composable
    override fun Show() = Unit

    private fun readResolve(): Any = AuthInitial
}