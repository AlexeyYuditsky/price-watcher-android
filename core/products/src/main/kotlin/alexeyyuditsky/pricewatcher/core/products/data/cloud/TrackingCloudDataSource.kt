package alexeyyuditsky.pricewatcher.core.products.data.cloud

import javax.inject.Inject
import retrofit2.HttpException

internal class TrackingCloudDataSource @Inject constructor(
    private val api: TrackingApi
) {

    suspend fun products(): ProductsResponse = api.products()

    suspend fun track(url: String): TrackedProductResponse = api.track(TrackRequest(url = url))

    suspend fun refresh(productId: String): TrackedProductResponse = api.refresh(productId)

    suspend fun remove(productId: String) {
        val response = api.remove(productId)
        if (!response.isSuccessful) throw HttpException(response)
    }

    suspend fun history(productId: String): HistoryResponse = api.history(productId)
}