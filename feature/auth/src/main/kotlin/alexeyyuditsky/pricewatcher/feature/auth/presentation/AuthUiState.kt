package alexeyyuditsky.pricewatcher.feature.auth.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import java.io.Serializable

internal sealed interface AuthUiState : Serializable {

    @Composable
    fun Show()

    fun allowsInput(): Boolean = true

    fun isEmailError(): Boolean = false

    fun isPasswordError(): Boolean = false

    fun keepsPassword(): Boolean = true

    @StringRes
    fun submitText(mode: AuthMode): Int = mode.submitText
}