package alexeyyuditsky.pricewatcher.core.products.domain

sealed interface PriceResult {

    fun <T> map(mapper: PriceResultMapper<T>): T
}