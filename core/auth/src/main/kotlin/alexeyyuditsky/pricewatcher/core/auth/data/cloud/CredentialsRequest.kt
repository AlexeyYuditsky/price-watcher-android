package alexeyyuditsky.pricewatcher.core.auth.data.cloud

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CredentialsRequest(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)