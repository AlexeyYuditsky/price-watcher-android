package alexeyyuditsky.pricewatcher.core.auth.data.cloud

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RefreshRequest(
    @SerialName("refreshToken") val refreshToken: String
)