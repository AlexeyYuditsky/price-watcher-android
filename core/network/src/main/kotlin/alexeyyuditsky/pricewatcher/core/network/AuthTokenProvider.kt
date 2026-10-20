package alexeyyuditsky.pricewatcher.core.network

interface AuthTokenProvider {

    fun accessToken(): String?

    fun refreshAccessToken(failedAccessToken: String?): String?
}