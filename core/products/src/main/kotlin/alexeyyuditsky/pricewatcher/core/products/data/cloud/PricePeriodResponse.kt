package alexeyyuditsky.pricewatcher.core.products.data.cloud

import java.math.BigDecimal
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PricePeriodResponse(
    @SerialName("from") val from: String? = null,
    @SerialName("to") val to: String? = null,
    @SerialName("amount")
    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal? = null,
    @SerialName("unitAmount")
    @Serializable(with = BigDecimalSerializer::class)
    val unitAmount: BigDecimal? = null,
    @SerialName("currency") val currency: String? = null
)