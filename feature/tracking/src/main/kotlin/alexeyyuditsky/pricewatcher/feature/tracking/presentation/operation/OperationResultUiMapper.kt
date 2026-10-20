package alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation

import alexeyyuditsky.pricewatcher.core.products.domain.PricePeriod
import alexeyyuditsky.pricewatcher.core.products.domain.TrackedProduct
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.mapper.TrackingFailureUiMapper
import javax.inject.Inject

internal class OperationResultUiMapper @Inject constructor() :
    TrackingFailureUiMapper<OperationUiState>(::OperationFailed) {

    override fun mapProduct(product: TrackedProduct): OperationUiState = OperationDone

    override fun mapRemoved(): OperationUiState = OperationDone

    override fun mapProducts(products: List<TrackedProduct>): OperationUiState = unexpected()

    override fun mapHistory(periods: List<PricePeriod>): OperationUiState = unexpected()
}