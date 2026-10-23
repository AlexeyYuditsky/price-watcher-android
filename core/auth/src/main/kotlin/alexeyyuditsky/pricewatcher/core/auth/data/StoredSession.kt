package alexeyyuditsky.pricewatcher.core.auth.data

import kotlinx.serialization.Serializable

@Serializable
internal data class StoredSession(
    val accessToken: String,
    val refreshToken: String
)