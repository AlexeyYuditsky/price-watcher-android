package alexeyyuditsky.pricewatcher.core.products.data.cloud

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HistoryResponse(
    @SerialName("periods") val periods: List<PricePeriodResponse>? = null
)