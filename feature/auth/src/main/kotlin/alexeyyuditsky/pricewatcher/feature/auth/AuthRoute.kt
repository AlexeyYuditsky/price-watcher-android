package alexeyyuditsky.pricewatcher.feature.auth

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.auth.presentation.AuthInitial
import alexeyyuditsky.pricewatcher.feature.auth.presentation.AuthMode
import alexeyyuditsky.pricewatcher.feature.auth.presentation.AuthPreviewActions
import alexeyyuditsky.pricewatcher.feature.auth.presentation.AuthScreen
import alexeyyuditsky.pricewatcher.feature.auth.presentation.AuthViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AuthRoute() {
    val viewModel: AuthViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val mode by viewModel.mode.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    AuthScreen(
        state = state,
        mode = mode,
        email = email,
        password = password,
        actions = viewModel
    )
}

@Preview(name = "Экран авторизации", showBackground = true)
@Composable
private fun AuthRoutePreview() {
    PricewatcherTheme {
        AuthScreen(
            state = AuthInitial,
            mode = AuthMode.LOGIN,
            email = "",
            password = "",
            actions = AuthPreviewActions
        )
    }
}