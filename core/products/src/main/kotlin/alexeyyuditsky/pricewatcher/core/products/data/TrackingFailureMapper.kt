package alexeyyuditsky.pricewatcher.core.products.data

import alexeyyuditsky.pricewatcher.core.products.domain.TrackingFailure
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import retrofit2.HttpException

internal class TrackingFailureMapper @Inject constructor() {

    fun map(error: Exception): TrackingFailure = when (error) {
        is HttpException -> when (error.code()) {
            400 -> TrackingFailure.INVALID_URL
            404 -> TrackingFailure.NOT_FOUND
            429 -> TrackingFailure.TOO_MANY_REQUESTS
            502 -> TrackingFailure.PRODUCT_UNAVAILABLE
            503 -> TrackingFailure.BUSY
            else -> TrackingFailure.SERVER
        }
        is SocketTimeoutException -> TrackingFailure.TIMEOUT
        is IOException -> TrackingFailure.NETWORK
        else -> TrackingFailure.INVALID_RESPONSE
    }
}