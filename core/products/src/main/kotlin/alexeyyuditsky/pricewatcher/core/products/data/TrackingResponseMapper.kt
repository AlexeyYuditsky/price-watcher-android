package alexeyyuditsky.pricewatcher.core.products.data

import alexeyyuditsky.pricewatcher.core.products.data.cloud.PricePointResponse
import alexeyyuditsky.pricewatcher.core.products.data.cloud.TrackedProductResponse
import alexeyyuditsky.pricewatcher.core.products.domain.PricePoint
import alexeyyuditsky.pricewatcher.core.products.domain.TrackedProduct
import java.time.Instant
import java.util.Currency
import javax.inject.Inject

internal class TrackingResponseMapper @Inject constructor() {

    fun product(response: TrackedProductResponse): TrackedProduct = TrackedProduct(
        id = checkNotNull(response.id?.takeIf(String::isNotBlank)) { "Missing id" },
        url = checkNotNull(response.url?.takeIf(String::isNotBlank)) { "Missing url" },
        title = response.title?.takeIf(String::isNotBlank),
        imageUrl = response.imageUrl?.takeIf { it.startsWith("https://") },
        latestPrice = response.latestPrice?.let(::pricePoint)
    )

    fun pricePoint(response: PricePointResponse): PricePoint {
        val amount = checkNotNull(response.amount) { "Missing amount" }
        val currency = checkNotNull(response.currency) { "Missing currency" }
        check(amount.signum() >= 0) { "Invalid amount" }
        Currency.getInstance(currency)
        return PricePoint(
            checkedAt = Instant.parse(checkNotNull(response.checkedAt) { "Missing checkedAt" }),
            amount = amount,
            currency = currency
        )
    }
}