package alexeyyuditsky.pricewatcher.core.auth.domain

data object AuthSuccessResult : AuthResult {

    override fun <T> map(mapper: AuthResultMapper<T>): T = mapper.mapSuccess()
}