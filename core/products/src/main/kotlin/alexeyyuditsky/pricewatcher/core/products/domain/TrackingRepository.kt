package alexeyyuditsky.pricewatcher.core.products.domain

interface TrackingRepository {

    suspend fun products(): TrackingResult

    suspend fun track(url: String): TrackingResult

    suspend fun refresh(productId: String): TrackingResult

    suspend fun remove(productId: String): TrackingResult

    suspend fun history(productId: String): TrackingResult
}