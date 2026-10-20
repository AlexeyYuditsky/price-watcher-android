package alexeyyuditsky.pricewatcher.core.products.domain

data class HistoryLoaded(
    private val periods: List<PricePeriod>
) : TrackingResult {

    override fun <T> map(mapper: TrackingResultMapper<T>): T = mapper.mapHistory(periods)
}