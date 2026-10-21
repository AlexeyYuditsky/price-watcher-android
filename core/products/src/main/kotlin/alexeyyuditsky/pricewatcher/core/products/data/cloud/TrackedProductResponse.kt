package alexeyyuditsky.pricewatcher.core.products.data.cloud

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TrackedProductResponse(
    @SerialName("id") val id: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("imageUrl") val imageUrl: String? = null,
    @SerialName("latestPrice") val latestPrice: PricePointResponse? = null
)