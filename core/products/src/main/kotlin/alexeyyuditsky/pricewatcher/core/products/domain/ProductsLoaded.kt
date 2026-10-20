package alexeyyuditsky.pricewatcher.core.products.domain

data class ProductsLoaded(
    private val products: List<TrackedProduct>
) : TrackingResult {

    override fun <T> map(mapper: TrackingResultMapper<T>): T = mapper.mapProducts(products)
}