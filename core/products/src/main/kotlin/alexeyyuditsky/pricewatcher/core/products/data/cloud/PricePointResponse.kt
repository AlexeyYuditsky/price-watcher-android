package alexeyyuditsky.pricewatcher.core.products.data.cloud

import java.math.BigDecimal
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PricePointResponse(
    @SerialName("checkedAt") val checkedAt: String? = null,
    @SerialName("amount")
    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal? = null,
    @SerialName("currency") val currency: String? = null
)