package alexeyyuditsky.pricewatcher.core.products.domain

enum class PriceFailure : PriceResult {
    INVALID_URL {
        override fun <T> map(mapper: PriceResultMapper<T>): T = mapper.mapInvalidUrl()
    },
    PRODUCT_UNAVAILABLE {
        override fun <T> map(mapper: PriceResultMapper<T>): T = mapper.mapProductUnavailable()
    },
    TOO_MANY_REQUESTS {
        override fun <T> map(mapper: PriceResultMapper<T>): T = mapper.mapTooManyRequests()
    },
    BUSY {
        override fun <T> map(mapper: PriceResultMapper<T>): T = mapper.mapBusy()
    },
    SERVER {
        override fun <T> map(mapper: PriceResultMapper<T>): T = mapper.mapServerError()
    },
    TIMEOUT {
        override fun <T> map(mapper: PriceResultMapper<T>): T = mapper.mapTimeout()
    },
    NETWORK {
        override fun <T> map(mapper: PriceResultMapper<T>): T = mapper.mapNetworkError()
    },
    INVALID_RESPONSE {
        override fun <T> map(mapper: PriceResultMapper<T>): T = mapper.mapInvalidResponse()
    }
}