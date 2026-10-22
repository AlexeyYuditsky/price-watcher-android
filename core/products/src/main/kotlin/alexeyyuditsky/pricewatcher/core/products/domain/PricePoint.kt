package alexeyyuditsky.pricewatcher.core.products.domain

import java.math.BigDecimal
import java.time.Instant

data class PricePoint(
    val checkedAt: Instant,
    val amount: BigDecimal,
    // The price per piece for packs of several pieces.
    val unitAmount: BigDecimal?,
    val currency: String,
    // The price before the latest change, in the same currency; absent until the price has changed.
    val previousAmount: BigDecimal?
)