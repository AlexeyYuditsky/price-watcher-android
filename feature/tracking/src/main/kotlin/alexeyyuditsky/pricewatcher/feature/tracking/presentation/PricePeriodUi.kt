package alexeyyuditsky.pricewatcher.feature.tracking.presentation

import java.io.Serializable

internal data class PricePeriodUi(
    val price: String,
    val unitPrice: String?,
    val period: String,
    // The move from the previous, older period.
    val priceChange: PriceChangeUi
) : Serializable