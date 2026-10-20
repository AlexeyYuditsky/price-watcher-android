package alexeyyuditsky.pricewatcher.core.auth.data

import alexeyyuditsky.pricewatcher.core.auth.domain.AuthFailure
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import retrofit2.HttpException

internal class AuthFailureMapper @Inject constructor() {

    fun map(error: Exception): AuthFailure = when (error) {
        is HttpException -> when (error.code()) {
            400 -> AuthFailure.INVALID_EMAIL
            401 -> AuthFailure.INVALID_CREDENTIALS
            409 -> AuthFailure.EMAIL_IN_USE
            429 -> AuthFailure.TOO_MANY_REQUESTS
            else -> AuthFailure.SERVER
        }
        is SocketTimeoutException -> AuthFailure.TIMEOUT
        is IOException -> AuthFailure.NETWORK
        else -> AuthFailure.INVALID_RESPONSE
    }
}