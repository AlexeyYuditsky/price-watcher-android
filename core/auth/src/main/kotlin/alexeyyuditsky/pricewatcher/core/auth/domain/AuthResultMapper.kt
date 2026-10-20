package alexeyyuditsky.pricewatcher.core.auth.domain

interface AuthResultMapper<T> {

    fun mapSuccess(): T

    fun mapInvalidEmail(): T

    fun mapShortPassword(): T

    fun mapInvalidCredentials(): T

    fun mapEmailInUse(): T

    fun mapTooManyRequests(): T

    fun mapServerError(): T

    fun mapTimeout(): T

    fun mapNetworkError(): T

    fun mapInvalidResponse(): T
}