package alexeyyuditsky.pricewatcher.data.cloud

import javax.inject.Inject

class CloudDataSource @Inject constructor(private val api: PriceApi) {
    suspend fun getPrice(url: String): PriceResponse = api.getPrice(PriceRequest(url))
}