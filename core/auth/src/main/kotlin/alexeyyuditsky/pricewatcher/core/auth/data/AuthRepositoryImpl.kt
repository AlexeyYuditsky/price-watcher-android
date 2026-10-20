package alexeyyuditsky.pricewatcher.core.auth.data

import alexeyyuditsky.pricewatcher.core.auth.data.cloud.AuthCloudDataSource
import alexeyyuditsky.pricewatcher.core.auth.data.cloud.SessionResponse
import alexeyyuditsky.pricewatcher.core.auth.domain.AuthRepository
import alexeyyuditsky.pricewatcher.core.auth.domain.AuthResult
import alexeyyuditsky.pricewatcher.core.auth.domain.AuthSuccessResult
import alexeyyuditsky.pricewatcher.core.auth.domain.AuthorizationStatus
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.flow.StateFlow

internal class AuthRepositoryImpl @Inject constructor(
    private val cloudDataSource: AuthCloudDataSource,
    private val storage: SessionStorage,
    private val responseMapper: SessionResponseMapper,
    private val failureMapper: AuthFailureMapper
) : AuthRepository {

    override val authorizationStatus: StateFlow<AuthorizationStatus> = storage.authorizationStatus

    override suspend fun login(email: String, password: String): AuthResult =
        authorize { cloudDataSource.login(email, password) }

    override suspend fun register(email: String, password: String): AuthResult =
        authorize { cloudDataSource.register(email, password) }

    override suspend fun logout() {
        val session = storage.read()
        storage.clear()
        if (session == null) return
        try {
            cloudDataSource.logout(session.accessToken)
        } catch (error: CancellationException) {
            throw error
        } catch (_: Exception) {
            // The local session is already cleared; the server session expires on its own.
        }
    }

    private suspend fun authorize(request: suspend () -> SessionResponse): AuthResult = try {
        storage.save(responseMapper.map(request()))
        AuthSuccessResult
    } catch (error: CancellationException) {
        throw error
    } catch (error: Exception) {
        failureMapper.map(error)
    }
}