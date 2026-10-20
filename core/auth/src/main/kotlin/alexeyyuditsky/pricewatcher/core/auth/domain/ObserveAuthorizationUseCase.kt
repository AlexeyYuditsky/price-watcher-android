package alexeyyuditsky.pricewatcher.core.auth.domain

import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

class ObserveAuthorizationUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(): StateFlow<AuthorizationStatus> = repository.authorizationStatus
}