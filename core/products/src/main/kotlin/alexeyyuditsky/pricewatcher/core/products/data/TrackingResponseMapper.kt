package alexeyyuditsky.pricewatcher.core.products.data

import alexeyyuditsky.pricewatcher.core.products.data.cloud.PricePeriodResponse
import alexeyyuditsky.pricewatcher.core.products.data.cloud.PricePointResponse
import alexeyyuditsky.pricewatcher.core.products.data.cloud.TrackedProductResponse
import alexeyyuditsky.pricewatcher.core.products.domain.PricePeriod
import alexeyyuditsky.pricewatcher.core.products.domain.PricePoint
import alexeyyuditsky.pricewatcher.core.products.domain.TrackedProduct
import java.math.BigDecimal
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

    fun pricePeriod(response: PricePeriodResponse): PricePeriod = PricePeriod(
        from = instant(response.from, "from"),
        to = instant(response.to, "to"),
        amount = amount(response.amount),
        unitAmount = response.unitAmount?.let(::amount),
        currency = currency(response.currency)
    )

    private fun pricePoint(response: PricePointResponse): PricePoint = PricePoint(
        checkedAt = instant(response.checkedAt, "checkedAt"),
        amount = amount(response.amount),
        unitAmount = response.unitAmount?.let(::amount),
        currency = currency(response.currency),
        previousAmount = response.previousAmount?.let(::amount)
    )

    private fun instant(value: String?, name: String): Instant =
        Instant.parse(checkNotNull(value) { "Missing $name" })

    private fun amount(value: BigDecimal?): BigDecimal {
        val amount = checkNotNull(value) { "Missing amount" }
        check(amount.signum() >= 0) { "Invalid amount" }
        return amount
    }

    private fun currency(value: String?): String {
        val currency = checkNotNull(value) { "Missing currency" }
        Currency.getInstance(currency)
        return currency
    }
}