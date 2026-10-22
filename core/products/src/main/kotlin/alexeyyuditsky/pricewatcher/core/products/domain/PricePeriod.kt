package alexeyyuditsky.pricewatcher.core.products.domain

import java.math.BigDecimal
import java.time.Instant

data class PricePeriod(
    val from: Instant,
    val to: Instant,
    val amount: BigDecimal,
    val unitAmount: BigDecimal?,
    val currency: String
)