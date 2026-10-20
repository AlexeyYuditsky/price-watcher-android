package alexeyyuditsky.pricewatcher.feature.tracking.presentation

import java.io.Serializable

internal data class PricePeriodUi(
    val price: String,
    val period: String,
    val checks: Int
) : Serializable