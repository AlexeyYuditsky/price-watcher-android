package alexeyyuditsky.pricewatcher.feature.auth.presentation.mapper

import alexeyyuditsky.pricewatcher.core.auth.domain.AuthResultMapper
import alexeyyuditsky.pricewatcher.feature.auth.R
import alexeyyuditsky.pricewatcher.feature.auth.presentation.AuthError
import alexeyyuditsky.pricewatcher.feature.auth.presentation.AuthSuccess
import alexeyyuditsky.pricewatcher.feature.auth.presentation.AuthUiState
import javax.inject.Inject

internal class AuthResultUiMapper @Inject constructor() : AuthResultMapper<AuthUiState> {

    override fun mapSuccess(): AuthUiState = AuthSuccess

    override fun mapInvalidEmail(): AuthUiState = AuthError(R.string.error_invalid_email)

    override fun mapShortPassword(): AuthUiState = AuthError(R.string.error_short_password)

    override fun mapInvalidCredentials(): AuthUiState = AuthError(R.string.error_invalid_credentials)

    override fun mapEmailInUse(): AuthUiState = AuthError(R.string.error_email_in_use)

    override fun mapTooManyRequests(): AuthUiState = AuthError(R.string.error_too_many_requests)

    override fun mapServerError(): AuthUiState = AuthError(R.string.error_server)

    override fun mapTimeout(): AuthUiState = AuthError(R.string.error_timeout)

    override fun mapNetworkError(): AuthUiState = AuthError(R.string.error_network)

    override fun mapInvalidResponse(): AuthUiState = AuthError(R.string.error_response)
}