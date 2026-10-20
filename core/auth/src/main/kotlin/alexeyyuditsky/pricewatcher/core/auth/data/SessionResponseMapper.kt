package alexeyyuditsky.pricewatcher.core.auth.data

import alexeyyuditsky.pricewatcher.core.auth.data.cloud.SessionResponse
import javax.inject.Inject

internal class SessionResponseMapper @Inject constructor() {

    fun map(response: SessionResponse): StoredSession = StoredSession(
        accessToken = checkNotNull(response.accessToken?.takeIf(String::isNotBlank)) {
            "Missing accessToken"
        },
        refreshToken = checkNotNull(response.refreshToken?.takeIf(String::isNotBlank)) {
            "Missing refreshToken"
        }
    )
}