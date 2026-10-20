package alexeyyuditsky.pricewatcher.core.products.domain

enum class TrackingFailure : TrackingResult {
    INVALID_URL {
        override fun <T> map(mapper: TrackingResultMapper<T>): T = mapper.mapInvalidUrl()
    },
    NOT_FOUND {
        override fun <T> map(mapper: TrackingResultMapper<T>): T = mapper.mapNotFound()
    },
    PRODUCT_UNAVAILABLE {
        override fun <T> map(mapper: TrackingResultMapper<T>): T = mapper.mapProductUnavailable()
    },
    TOO_MANY_REQUESTS {
        override fun <T> map(mapper: TrackingResultMapper<T>): T = mapper.mapTooManyRequests()
    },
    BUSY {
        override fun <T> map(mapper: TrackingResultMapper<T>): T = mapper.mapBusy()
    },
    SERVER {
        override fun <T> map(mapper: TrackingResultMapper<T>): T = mapper.mapServerError()
    },
    TIMEOUT {
        override fun <T> map(mapper: TrackingResultMapper<T>): T = mapper.mapTimeout()
    },
    NETWORK {
        override fun <T> map(mapper: TrackingResultMapper<T>): T = mapper.mapNetworkError()
    },
    INVALID_RESPONSE {
        override fun <T> map(mapper: TrackingResultMapper<T>): T = mapper.mapInvalidResponse()
    }
}