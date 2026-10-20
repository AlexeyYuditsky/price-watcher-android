package alexeyyuditsky.pricewatcher.feature.auth.presentation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.auth.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun AuthScreen(
    state: AuthUiState,
    mode: AuthMode,
    email: String,
    password: String,
    actions: AuthActions
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val submit = {
        keyboard?.hide()
        actions.submit()
    }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(mode.title),
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = stringResource(R.string.auth_description),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            OutlinedTextField(
                value = email,
                onValueChange = actions::changeEmail,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("authEmail"),
                enabled = state.allowsInput(),
                label = { Text(text = stringResource(R.string.email)) },
                singleLine = true,
                isError = state.isEmailError(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
            OutlinedTextField(
                value = password,
                onValueChange = actions::changePassword,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("authPassword"),
                enabled = state.allowsInput(),
                label = { Text(text = stringResource(R.string.password)) },
                supportingText = { Text(text = stringResource(R.string.password_hint)) },
                singleLine = true,
                isError = state.isPasswordError(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { submit() })
            )
            Button(
                onClick = submit,
                enabled = email.isNotBlank() && password.isNotEmpty() && state.allowsInput(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("authSubmit")
            ) {
                Text(text = stringResource(state.submitText(mode)))
            }
            TextButton(
                onClick = actions::switchMode,
                enabled = state.allowsInput(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("authSwitchMode")
            ) {
                Text(text = stringResource(mode.switchText))
            }
            state.Show()
        }
    }
}

@Preview(name = "Вход", showBackground = true)
@Composable
private fun AuthScreenLoginPreview() {
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

@Preview(name = "Регистрация", showBackground = true)
@Composable
private fun AuthScreenRegisterPreview() {
    PricewatcherTheme {
        AuthScreen(
            state = AuthInitial,
            mode = AuthMode.REGISTER,
            email = "alexey@example.com",
            password = "pass",
            actions = AuthPreviewActions
        )
    }
}

@Preview(name = "Вход · загрузка", showBackground = true)
@Composable
private fun AuthScreenLoadingPreview() {
    PricewatcherTheme {
        AuthScreen(
            state = AuthLoading,
            mode = AuthMode.LOGIN,
            email = "alexey@example.com",
            password = "pass",
            actions = AuthPreviewActions
        )
    }
}

@Preview(name = "Вход · ошибка", showBackground = true)
@Composable
private fun AuthScreenErrorPreview() {
    PricewatcherTheme {
        AuthScreen(
            state = AuthError(R.string.error_invalid_credentials),
            mode = AuthMode.LOGIN,
            email = "alexey@example.com",
            password = "pass",
            actions = AuthPreviewActions
        )
    }
}