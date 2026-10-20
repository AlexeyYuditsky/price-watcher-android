package alexeyyuditsky.pricewatcher.core.network

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor @Inject constructor(
    private val tokenProvider: AuthTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.accessToken() ?: return chain.proceed(chain.request())
        return chain.proceed(
            chain.request()
                .newBuilder()
                .header(AUTHORIZATION, "Bearer $token")
                .build()
        )
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
    }
}