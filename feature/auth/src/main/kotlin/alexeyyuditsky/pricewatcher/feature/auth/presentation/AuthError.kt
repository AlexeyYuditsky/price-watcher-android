package alexeyyuditsky.pricewatcher.feature.auth.presentation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.auth.R
import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

internal data class AuthError(
    @field:StringRes private val message: Int
) : AuthUiState {

    override fun isEmailError(): Boolean =
        message == R.string.error_invalid_email || message == R.string.error_email_in_use

    override fun isPasswordError(): Boolean =
        message == R.string.error_short_password || message == R.string.error_invalid_credentials

    @Composable
    override fun Show() {
        Text(
            text = stringResource(message),
            modifier = Modifier.testTag("authError"),
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview(name = "Авторизация · ошибка", showBackground = true)
@Composable
private fun AuthErrorPreview() {
    PricewatcherTheme {
        AuthError(R.string.error_invalid_credentials).Show()
    }
}