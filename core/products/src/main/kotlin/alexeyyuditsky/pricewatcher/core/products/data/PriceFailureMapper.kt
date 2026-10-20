package alexeyyuditsky.pricewatcher.core.products.data

import alexeyyuditsky.pricewatcher.core.products.domain.PriceFailure
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import retrofit2.HttpException

internal class PriceFailureMapper @Inject constructor() {

    fun map(error: Exception): PriceFailure = when (error) {
        is HttpException -> when (error.code()) {
            400 -> PriceFailure.INVALID_URL
            429 -> PriceFailure.TOO_MANY_REQUESTS
            502 -> PriceFailure.PRODUCT_UNAVAILABLE
            503 -> PriceFailure.BUSY
            else -> PriceFailure.SERVER
        }
        is SocketTimeoutException -> PriceFailure.TIMEOUT
        is IOException -> PriceFailure.NETWORK
        else -> PriceFailure.INVALID_RESPONSE
    }
}