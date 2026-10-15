package alexeyyuditsky.pricewatcher.domain

import java.math.BigDecimal
import java.time.Instant

data class ProductPrice(
    val amount: BigDecimal,
    val currency: String,
    val fetchedAt: Instant
)