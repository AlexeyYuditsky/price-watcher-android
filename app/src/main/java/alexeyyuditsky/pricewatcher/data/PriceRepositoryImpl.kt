package alexeyyuditsky.pricewatcher.data

import alexeyyuditsky.pricewatcher.data.cloud.CloudDataSource
import alexeyyuditsky.pricewatcher.domain.PriceRepository
import alexeyyuditsky.pricewatcher.domain.ProductPrice
import java.time.Instant
import java.util.Currency
import javax.inject.Inject

class PriceRepositoryImpl @Inject constructor(private val cloudDataSource: CloudDataSource) : PriceRepository {
    override suspend fun getPrice(url: String): ProductPrice {
        val response = cloudDataSource.getPrice(url)
        val amount = checkNotNull(response.amount) { "Missing amount" }
        val currency = checkNotNull(response.currency) { "Missing currency" }
        check(amount.signum() >= 0) { "Invalid amount" }
        check(runCatching { Currency.getInstance(currency) }.isSuccess) { "Invalid currency" }
        val fetchedAt = runCatching {
            Instant.parse(checkNotNull(response.fetchedAt) { "Missing fetchedAt" })
        }.getOrElse { throw IllegalStateException("Invalid fetchedAt", it) }
        return ProductPrice(amount, currency, fetchedAt)
    }
}