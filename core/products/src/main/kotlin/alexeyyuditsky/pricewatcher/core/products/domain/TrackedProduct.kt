package alexeyyuditsky.pricewatcher.core.products.domain

data class TrackedProduct(
    val id: String,
    val url: String,
    val title: String?,
    val imageUrl: String?,
    val latestPrice: PricePoint?
)