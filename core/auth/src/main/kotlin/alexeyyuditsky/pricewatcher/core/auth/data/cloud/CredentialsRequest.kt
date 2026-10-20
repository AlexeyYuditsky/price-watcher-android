package alexeyyuditsky.pricewatcher.core.auth.data.cloud

import com.google.gson.annotations.SerializedName

internal data class CredentialsRequest(
    @field:SerializedName("email") val email: String,
    @field:SerializedName("password") val password: String
)