package alexeyyuditsky.pricewatcher.core.products.data.cloud

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface TrackingApi {

    @GET("tracking/products")
    suspend fun products(): ProductsResponse

    @POST("tracking/products")
    suspend fun track(@Body request: TrackRequest): TrackedProductResponse

    @POST("tracking/products/{id}/refresh")
    suspend fun refresh(@Path("id") productId: String): TrackedProductResponse

    @DELETE("tracking/products/{id}")
    suspend fun remove(@Path("id") productId: String): Response<Unit>

    @GET("tracking/products/{id}/history")
    suspend fun history(@Path("id") productId: String): HistoryResponse
}