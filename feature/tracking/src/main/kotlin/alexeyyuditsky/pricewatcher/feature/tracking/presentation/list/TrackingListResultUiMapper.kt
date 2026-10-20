package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import alexeyyuditsky.pricewatcher.core.products.domain.PricePeriod
import alexeyyuditsky.pricewatcher.core.products.domain.TrackedProduct
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.mapper.TrackingFailureUiMapper
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.mapper.TrackingUiFormatter
import javax.inject.Inject

internal class TrackingListResultUiMapper @Inject constructor(
    private val formatter: TrackingUiFormatter
) : TrackingFailureUiMapper<TrackingListUiState>(::TrackingListError) {

    override fun mapProducts(products: List<TrackedProduct>): TrackingListUiState =
        TrackingListContent(products.map(formatter::product))

    override fun mapProduct(product: TrackedProduct): TrackingListUiState = unexpected()

    override fun mapHistory(periods: List<PricePeriod>): TrackingListUiState = unexpected()

    override fun mapRemoved(): TrackingListUiState = unexpected()
}