package alexeyyuditsky.pricewatcher.feature.auth.presentation

import alexeyyuditsky.pricewatcher.core.auth.domain.LoginUseCase
import alexeyyuditsky.pricewatcher.core.auth.domain.RegisterUseCase
import alexeyyuditsky.pricewatcher.core.common.RunAsync
import alexeyyuditsky.pricewatcher.feature.auth.presentation.mapper.AuthResultUiMapper
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
internal class AuthViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val login: LoginUseCase,
    private val register: RegisterUseCase,
    private val runAsync: RunAsync,
    private val resultMapper: AuthResultUiMapper
) : ViewModel(), AuthActions {

    val state: StateFlow<AuthUiState> = savedStateHandle.getStateFlow(STATE_KEY, AuthInitial)
    val mode: StateFlow<AuthMode> = savedStateHandle.getStateFlow(MODE_KEY, AuthMode.LOGIN)
    val email: StateFlow<String> = savedStateHandle.getStateFlow(EMAIL_KEY, "")
    val password: StateFlow<String> = savedStateHandle.getStateFlow(PASSWORD_KEY, "")

    init {
        if (state.value is AuthLoading) update(AuthInitial)
    }

    override fun changeEmail(email: String) {
        if (!state.value.allowsInput()) return
        savedStateHandle[EMAIL_KEY] = email
        update(AuthInitial)
    }

    override fun changePassword(password: String) {
        if (!state.value.allowsInput()) return
        savedStateHandle[PASSWORD_KEY] = password
        update(AuthInitial)
    }

    override fun switchMode() {
        if (!state.value.allowsInput()) return
        savedStateHandle[MODE_KEY] = mode.value.toggle()
        update(AuthInitial)
    }

    override fun submit() {
        if (!state.value.allowsInput()) return
        val email = email.value
        val password = password.value
        val isRegistration = mode.value == AuthMode.REGISTER
        update(AuthLoading)
        runAsync.runAsync(
            scope = viewModelScope,
            background = {
                val result = if (isRegistration) register(email, password) else login(email, password)
                result.map(resultMapper)
            }
        ) { result ->
            val next = result.getOrElse { resultMapper.mapInvalidResponse() }
            if (!next.keepsPassword()) savedStateHandle[PASSWORD_KEY] = ""
            update(next)
        }
    }

    private fun update(state: AuthUiState) {
        savedStateHandle[STATE_KEY] = state
    }

    companion object {
        private const val STATE_KEY = "auth_ui_state"
        private const val MODE_KEY = "auth_mode"
        private const val EMAIL_KEY = "auth_email"
        private const val PASSWORD_KEY = "auth_password"
    }
}