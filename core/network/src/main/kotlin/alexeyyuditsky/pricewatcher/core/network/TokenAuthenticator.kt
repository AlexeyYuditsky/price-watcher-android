package alexeyyuditsky.pricewatcher.core.network

import javax.inject.Inject
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

internal class TokenAuthenticator @Inject constructor(
    private val tokenProvider: AuthTokenProvider
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.priorResponse != null) return null
        val failedToken = response.request
            .header(AuthInterceptor.AUTHORIZATION)
            ?.removePrefix("Bearer ")
        val token = tokenProvider.refreshAccessToken(failedToken) ?: return null
        return response.request
            .newBuilder()
            .header(AuthInterceptor.AUTHORIZATION, "Bearer $token")
            .build()
    }
}