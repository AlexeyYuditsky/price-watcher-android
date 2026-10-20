package alexeyyuditsky.pricewatcher.feature.tracking.presentation.mapper

import alexeyyuditsky.pricewatcher.core.products.domain.TrackingResultMapper
import alexeyyuditsky.pricewatcher.feature.tracking.R
import androidx.annotation.StringRes

internal abstract class TrackingFailureUiMapper<T>(
    private val error: (Int) -> T
) : TrackingResultMapper<T> {

    protected fun unexpected(): T = mapInvalidResponse()

    override fun mapInvalidUrl(): T = failure(R.string.error_invalid_url)

    override fun mapNotFound(): T = failure(R.string.error_not_found)

    override fun mapProductUnavailable(): T = failure(R.string.error_product_unavailable)

    override fun mapTooManyRequests(): T = failure(R.string.error_too_many_requests)

    override fun mapBusy(): T = failure(R.string.error_busy)

    override fun mapServerError(): T = failure(R.string.error_server)

    override fun mapTimeout(): T = failure(R.string.error_timeout)

    override fun mapNetworkError(): T = failure(R.string.error_network)

    override fun mapInvalidResponse(): T = failure(R.string.error_response)

    private fun failure(@StringRes message: Int): T = error(message)
}