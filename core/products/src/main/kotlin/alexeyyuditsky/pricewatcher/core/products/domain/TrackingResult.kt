package alexeyyuditsky.pricewatcher.core.products.domain

sealed interface TrackingResult {

    fun <T> map(mapper: TrackingResultMapper<T>): T
}