package alexeyyuditsky.pricewatcher.core.products.domain

data object ProductRemoved : TrackingResult {

    override fun <T> map(mapper: TrackingResultMapper<T>): T = mapper.mapRemoved()
}