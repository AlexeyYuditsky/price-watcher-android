package alexeyyuditsky.pricewatcher.data.cloud

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST
import java.math.BigDecimal

interface PriceApi {
    @POST("products/price")
    suspend fun getPrice(@Body request: PriceRequest): PriceResponse
}

data class PriceRequest(@field:SerializedName("url") val url: String)

data class PriceResponse(
    @field:SerializedName("amount") val amount: BigDecimal? = null,
    @field:SerializedName("currency") val currency: String? = null,
    @field:SerializedName("fetchedAt") val fetchedAt: String? = null
)