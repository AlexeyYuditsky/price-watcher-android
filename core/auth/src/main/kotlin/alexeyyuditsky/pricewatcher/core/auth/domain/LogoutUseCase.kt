package alexeyyuditsky.pricewatcher.core.auth.domain

import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke() = repository.logout()
}