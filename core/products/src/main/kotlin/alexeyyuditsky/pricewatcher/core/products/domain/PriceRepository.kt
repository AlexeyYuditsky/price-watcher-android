package alexeyyuditsky.pricewatcher.core.products.domain

interface PriceRepository {

    suspend fun getPrice(url: String): PriceResult
}