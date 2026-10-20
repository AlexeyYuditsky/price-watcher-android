package alexeyyuditsky.pricewatcher.core.auth.data.cloud

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface AuthApi {

    @POST("auth/register")
    suspend fun register(@Body request: CredentialsRequest): SessionResponse

    @POST("auth/login")
    suspend fun login(@Body request: CredentialsRequest): SessionResponse

    @POST("auth/refresh")
    fun refresh(@Body request: RefreshRequest): Call<SessionResponse>

    @POST("auth/logout")
    suspend fun logout(@Header("Authorization") authorization: String)
}