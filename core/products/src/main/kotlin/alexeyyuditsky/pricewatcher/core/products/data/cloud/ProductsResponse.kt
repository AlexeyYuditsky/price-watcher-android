package alexeyyuditsky.pricewatcher.core.products.data.cloud

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProductsResponse(
    @SerialName("products") val products: List<TrackedProductResponse>? = null
)