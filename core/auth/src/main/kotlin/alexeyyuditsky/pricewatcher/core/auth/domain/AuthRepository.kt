package alexeyyuditsky.pricewatcher.core.auth.domain

import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    val authorizationStatus: StateFlow<AuthorizationStatus>

    suspend fun login(email: String, password: String): AuthResult

    suspend fun register(email: String, password: String): AuthResult

    suspend fun logout()
}