package alexeyyuditsky.pricewatcher.core.auth.domain

import javax.inject.Inject

class CredentialsValidator @Inject constructor() {

    fun validate(email: String, password: String): AuthFailure? = when {
        !EMAIL_REGEX.matches(email.trim()) -> AuthFailure.INVALID_EMAIL
        password.length < MIN_PASSWORD_LENGTH -> AuthFailure.SHORT_PASSWORD
        else -> null
    }

    private companion object {
        const val MIN_PASSWORD_LENGTH = 4
        val EMAIL_REGEX = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$")
    }
}