package alexeyyuditsky.pricewatcher.core.products.data.cloud

import javax.inject.Inject

internal class CloudDataSource @Inject constructor(
    private val api: PriceApi
) {

    suspend fun getPrice(url: String): PriceResponse = api.getPrice(PriceRequest(url = url))
}