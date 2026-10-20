package alexeyyuditsky.pricewatcher.core.products.domain

import java.math.BigDecimal
import java.time.Instant

data class PricePoint(
    val checkedAt: Instant,
    val amount: BigDecimal,
    val currency: String
)