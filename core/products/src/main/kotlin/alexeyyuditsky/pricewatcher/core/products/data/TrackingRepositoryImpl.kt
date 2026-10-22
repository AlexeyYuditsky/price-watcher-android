package alexeyyuditsky.pricewatcher.core.products.data

import alexeyyuditsky.pricewatcher.core.products.data.cloud.TrackingCloudDataSource
import alexeyyuditsky.pricewatcher.core.products.domain.HistoryLoaded
import alexeyyuditsky.pricewatcher.core.products.domain.ProductRemoved
import alexeyyuditsky.pricewatcher.core.products.domain.ProductSaved
import alexeyyuditsky.pricewatcher.core.products.domain.ProductsLoaded
import alexeyyuditsky.pricewatcher.core.products.domain.TrackingRepository
import alexeyyuditsky.pricewatcher.core.products.domain.TrackingResult
import alexeyyuditsky.pricewatcher.core.products.domain.priceDropsFirst
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

internal class TrackingRepositoryImpl @Inject constructor(
    private val cloudDataSource: TrackingCloudDataSource,
    private val responseMapper: TrackingResponseMapper,
    private val failureMapper: TrackingFailureMapper
): TrackingRepository {

    override suspend fun products(): TrackingResult = handle {
        ProductsLoaded(
            cloudDataSource.products().products.orEmpty()
                .map(responseMapper::product)
                .priceDropsFirst()
        )
    }

    override suspend fun track(url: String): TrackingResult = handle {
        ProductSaved(responseMapper.product(cloudDataSource.track(url)))
    }

    override suspend fun refresh(productId: String): TrackingResult = handle {
        ProductSaved(responseMapper.product(cloudDataSource.refresh(productId)))
    }

    override suspend fun remove(productId: String): TrackingResult = handle {
        cloudDataSource.remove(productId)
        ProductRemoved
    }

    override suspend fun history(productId: String): TrackingResult = handle {
        HistoryLoaded(cloudDataSource.history(productId).periods.orEmpty().map(responseMapper::pricePeriod))
    }

    private suspend fun handle(request: suspend () -> TrackingResult): TrackingResult = try {
        request()
    } catch (error: CancellationException) {
        throw error
    } catch (error: Exception) {
        failureMapper.map(error)
    }
}