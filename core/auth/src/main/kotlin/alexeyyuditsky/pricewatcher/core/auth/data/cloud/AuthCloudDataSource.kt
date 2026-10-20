package alexeyyuditsky.pricewatcher.core.auth.data.cloud

import javax.inject.Inject
import retrofit2.HttpException

internal class AuthCloudDataSource @Inject constructor(
    private val api: AuthApi
) {

    suspend fun register(email: String, password: String): SessionResponse =
        api.register(CredentialsRequest(email = email, password = password))

    suspend fun login(email: String, password: String): SessionResponse =
        api.login(CredentialsRequest(email = email, password = password))

    fun refresh(refreshToken: String): SessionResponse {
        val response = api.refresh(RefreshRequest(refreshToken = refreshToken)).execute()
        if (!response.isSuccessful) throw HttpException(response)
        return checkNotNull(response.body()) { "Empty refresh response" }
    }

    suspend fun logout(accessToken: String) = api.logout("Bearer $accessToken")
}