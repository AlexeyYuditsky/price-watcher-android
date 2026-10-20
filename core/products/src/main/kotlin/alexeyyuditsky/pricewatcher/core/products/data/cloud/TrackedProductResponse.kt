package alexeyyuditsky.pricewatcher.core.products.data.cloud

import com.google.gson.annotations.SerializedName

internal data class TrackedProductResponse(
    @field:SerializedName("id") val id: String? = null,
    @field:SerializedName("url") val url: String? = null,
    @field:SerializedName("title") val title: String? = null,
    @field:SerializedName("imageUrl") val imageUrl: String? = null,
    @field:SerializedName("latestPrice") val latestPrice: PricePointResponse? = null
)