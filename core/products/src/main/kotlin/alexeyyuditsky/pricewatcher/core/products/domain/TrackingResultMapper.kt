package alexeyyuditsky.pricewatcher.core.products.domain

interface TrackingResultMapper<T> {

    fun mapProducts(products: List<TrackedProduct>): T

    fun mapProduct(product: TrackedProduct): T

    fun mapHistory(periods: List<PricePeriod>): T

    fun mapRemoved(): T

    fun mapInvalidUrl(): T

    fun mapNotFound(): T

    fun mapProductUnavailable(): T

    fun mapTooManyRequests(): T

    fun mapBusy(): T

    fun mapServerError(): T

    fun mapTimeout(): T

    fun mapNetworkError(): T

    fun mapInvalidResponse(): T
}