package alexeyyuditsky.pricewatcher.core.auth.data

import alexeyyuditsky.pricewatcher.core.auth.data.cloud.AuthCloudDataSource
import alexeyyuditsky.pricewatcher.core.network.AuthTokenProvider
import java.io.IOException
import java.security.GeneralSecurityException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

// OkHttp calls the provider on its worker threads, so blocking here never touches the main thread.
@Singleton
internal class SessionTokenProvider @Inject constructor(
    private val storage: SessionStorage,
    private val cloudDataSource: AuthCloudDataSource,
    private val responseMapper: SessionResponseMapper
) : AuthTokenProvider {

    override fun accessToken(): String? = runBlocking { storage.read()?.accessToken }

    @Synchronized
    override fun refreshAccessToken(failedAccessToken: String?): String? = runBlocking {
        val session = storage.read() ?: return@runBlocking null
        if (session.accessToken != failedAccessToken) return@runBlocking session.accessToken
        try {
            responseMapper.map(cloudDataSource.refresh(session.refreshToken))
                .also { storage.save(it) }
                .accessToken
        } catch (error: HttpException) {
            if (error.code() == 401) storage.clear()
            null
        } catch (_: IOException) {
            null
        } catch (_: IllegalStateException) {
            null
        } catch (_: GeneralSecurityException) {
            null
        }
    }
}