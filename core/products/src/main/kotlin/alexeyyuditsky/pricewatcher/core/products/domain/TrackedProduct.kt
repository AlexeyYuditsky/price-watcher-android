package alexeyyuditsky.pricewatcher.core.products.domain

import java.math.BigDecimal

data class TrackedProduct(
    val id: String,
    val url: String,
    val title: String?,
    val imageUrl: String?,
    val latestPrice: PricePoint?
) {

    // How much the latest change lowered the price; null when it rose, stayed or is unknown.
    fun priceDrop(): BigDecimal? = latestPrice?.let { price ->
        price.previousAmount?.minus(price.amount)?.takeIf { it.signum() > 0 }
    }

    // How much the latest change raised the price; null when it dropped, stayed or is unknown.
    fun priceRise(): BigDecimal? = latestPrice?.let { price ->
        price.previousAmount?.let { price.amount.minus(it) }?.takeIf { it.signum() > 0 }
    }
}