package alexeyyuditsky.pricewatcher.core.auth.domain

sealed interface AuthResult {

    fun <T> map(mapper: AuthResultMapper<T>): T
}