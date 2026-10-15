package alexeyyuditsky.pricewatcher.core.products.data.cloud

import retrofit2.http.Body
import retrofit2.http.POST

internal interface PriceApi {

    @POST("products/price")
    suspend fun getPrice(@Body request: PriceRequest): PriceResponse
}