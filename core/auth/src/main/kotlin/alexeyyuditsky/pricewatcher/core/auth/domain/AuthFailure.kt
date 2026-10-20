package alexeyyuditsky.pricewatcher.core.auth.domain

enum class AuthFailure : AuthResult {
    INVALID_EMAIL {
        override fun <T> map(mapper: AuthResultMapper<T>): T = mapper.mapInvalidEmail()
    },
    SHORT_PASSWORD {
        override fun <T> map(mapper: AuthResultMapper<T>): T = mapper.mapShortPassword()
    },
    INVALID_CREDENTIALS {
        override fun <T> map(mapper: AuthResultMapper<T>): T = mapper.mapInvalidCredentials()
    },
    EMAIL_IN_USE {
        override fun <T> map(mapper: AuthResultMapper<T>): T = mapper.mapEmailInUse()
    },
    TOO_MANY_REQUESTS {
        override fun <T> map(mapper: AuthResultMapper<T>): T = mapper.mapTooManyRequests()
    },
    SERVER {
        override fun <T> map(mapper: AuthResultMapper<T>): T = mapper.mapServerError()
    },
    TIMEOUT {
        override fun <T> map(mapper: AuthResultMapper<T>): T = mapper.mapTimeout()
    },
    NETWORK {
        override fun <T> map(mapper: AuthResultMapper<T>): T = mapper.mapNetworkError()
    },
    INVALID_RESPONSE {
        override fun <T> map(mapper: AuthResultMapper<T>): T = mapper.mapInvalidResponse()
    }
}