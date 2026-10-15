package alexeyyuditsky.pricewatcher.core.products.data

import alexeyyuditsky.pricewatcher.core.products.data.cloud.PriceResponse
import alexeyyuditsky.pricewatcher.core.products.domain.ProductPrice
import java.time.Instant
import java.util.Currency
import javax.inject.Inject

internal class PriceResponseMapper @Inject constructor() {

    fun map(response: PriceResponse): ProductPrice {
        val amount = checkNotNull(response.amount) { "Missing amount" }
        val currency = checkNotNull(response.currency) { "Missing currency" }
        check(amount.signum() >= 0) { "Invalid amount" }
        Currency.getInstance(currency)
        val fetchedAt = Instant.parse(checkNotNull(response.fetchedAt) { "Missing fetchedAt" })
        return ProductPrice(amount = amount, currency = currency, fetchedAt = fetchedAt)
    }
}