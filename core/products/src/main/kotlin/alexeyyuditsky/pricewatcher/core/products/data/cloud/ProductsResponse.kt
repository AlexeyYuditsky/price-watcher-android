package alexeyyuditsky.pricewatcher.core.products.data.cloud

import com.google.gson.annotations.SerializedName

internal data class ProductsResponse(
    @field:SerializedName("products") val products: List<TrackedProductResponse>? = null
)