package alexeyyuditsky.pricewatcher.core.auth.data.cloud

import com.google.gson.annotations.SerializedName

internal data class RefreshRequest(
    @field:SerializedName("refreshToken") val refreshToken: String
)