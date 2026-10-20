package alexeyyuditsky.pricewatcher.core.auth.data

internal data class StoredSession(
    val accessToken: String,
    val refreshToken: String
)