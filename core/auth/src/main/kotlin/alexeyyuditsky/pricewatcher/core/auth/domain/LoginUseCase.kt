package alexeyyuditsky.pricewatcher.core.auth.domain

import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val validator: CredentialsValidator
) {

    suspend operator fun invoke(email: String, password: String): AuthResult =
        validator.validate(email, password) ?: repository.login(email.trim(), password)
}