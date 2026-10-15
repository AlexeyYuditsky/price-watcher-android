package alexeyyuditsky.pricewatcher.domain

interface PriceRepository {
    suspend fun getPrice(url: String): ProductPrice
}