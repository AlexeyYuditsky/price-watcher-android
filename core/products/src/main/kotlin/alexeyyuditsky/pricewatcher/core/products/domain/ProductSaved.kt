package alexeyyuditsky.pricewatcher.core.products.domain

data class ProductSaved(
    private val product: TrackedProduct
) : TrackingResult {

    override fun <T> map(mapper: TrackingResultMapper<T>): T = mapper.mapProduct(product)
}