package alexeyyuditsky.pricewatcher.feature.tracking.presentation.history

import alexeyyuditsky.pricewatcher.core.products.domain.PricePeriod
import alexeyyuditsky.pricewatcher.core.products.domain.TrackedProduct
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.mapper.TrackingFailureUiMapper
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.mapper.TrackingUiFormatter
import javax.inject.Inject

internal class HistoryResultUiMapper @Inject constructor(
    private val formatter: TrackingUiFormatter
) : TrackingFailureUiMapper<HistoryUiState>(::HistoryError) {

    override fun mapHistory(periods: List<PricePeriod>): HistoryUiState =
        HistoryContent(periods.map(formatter::period))

    override fun mapProducts(products: List<TrackedProduct>): HistoryUiState = unexpected()

    override fun mapProduct(product: TrackedProduct): HistoryUiState = unexpected()

    override fun mapRemoved(): HistoryUiState = unexpected()
}