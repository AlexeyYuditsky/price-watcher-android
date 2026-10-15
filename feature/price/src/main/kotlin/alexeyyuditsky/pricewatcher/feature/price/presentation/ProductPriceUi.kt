package alexeyyuditsky.pricewatcher.feature.price.presentation

import java.io.Serializable

internal data class ProductPriceUi(
    val amount: String,
    val fetchedAt: String
) : Serializable