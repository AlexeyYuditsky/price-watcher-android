package alexeyyuditsky.pricewatcher.core.products.data

import alexeyyuditsky.pricewatcher.core.products.data.cloud.CloudDataSource
import alexeyyuditsky.pricewatcher.core.products.domain.PriceRepository
import alexeyyuditsky.pricewatcher.core.products.domain.PriceResult
import alexeyyuditsky.pricewatcher.core.products.domain.PriceSuccessResult
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

internal class PriceRepositoryImpl @Inject constructor(
    private val cloudDataSource: CloudDataSource,
    private val responseMapper: PriceResponseMapper,
    private val failureMapper: PriceFailureMapper
) : PriceRepository {

    override suspend fun getPrice(url: String): PriceResult = try {
        PriceSuccessResult(responseMapper.map(cloudDataSource.getPrice(url)))
    } catch (error: CancellationException) {
        throw error
    } catch (error: Exception) {
        failureMapper.map(error)
    }
}