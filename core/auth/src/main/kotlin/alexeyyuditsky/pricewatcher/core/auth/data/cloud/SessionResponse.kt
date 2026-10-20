package alexeyyuditsky.pricewatcher.core.auth.data.cloud

import com.google.gson.annotations.SerializedName

internal data class SessionResponse(
    @field:SerializedName("accessToken") val accessToken: String? = null,
    @field:SerializedName("refreshToken") val refreshToken: String? = null
)