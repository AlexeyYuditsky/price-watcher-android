package alexeyyuditsky.pricewatcher.core.products.data.cloud

import com.google.gson.annotations.SerializedName

internal data class TrackRequest(
    @field:SerializedName("url") val url: String
)