package alexeyyuditsky.pricewatcher.feature.tracking.presentation

import java.io.Serializable

internal data class TrackedProductUi(
    val id: String,
    val url: String,
    val title: String?,
    val imageUrl: String?,
    val price: String?,
    val priceChange: PriceChangeUi,
    val unitPrice: String?,
    val checkedAt: String?
) : Serializable