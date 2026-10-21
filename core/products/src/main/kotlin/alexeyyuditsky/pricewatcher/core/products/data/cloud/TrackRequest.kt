package alexeyyuditsky.pricewatcher.core.products.data.cloud

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TrackRequest(
    @SerialName("url") val url: String
)