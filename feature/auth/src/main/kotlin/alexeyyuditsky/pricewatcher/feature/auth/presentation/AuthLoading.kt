package alexeyyuditsky.pricewatcher.feature.auth.presentation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.auth.R
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

internal data object AuthLoading : AuthUiState {

    override fun allowsInput(): Boolean = false

    override fun submitText(mode: AuthMode): Int = R.string.authorizing

    @Composable
    override fun Show() {
        CircularProgressIndicator(modifier = Modifier.testTag("authLoading"))
    }

    private fun readResolve(): Any = AuthLoading
}

@Preview(name = "Авторизация · загрузка", showBackground = true)
@Composable
private fun AuthLoadingPreview() {
    PricewatcherTheme {
        AuthLoading.Show()
    }
}