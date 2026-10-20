package alexeyyuditsky.pricewatcher.core.products.domain

interface PriceResultMapper<T> {

    fun mapSuccess(price: ProductPrice): T

    fun mapInvalidUrl(): T

    fun mapProductUnavailable(): T

    fun mapTooManyRequests(): T

    fun mapBusy(): T

    fun mapServerError(): T

    fun mapTimeout(): T

    fun mapNetworkError(): T

    fun mapInvalidResponse(): T
}