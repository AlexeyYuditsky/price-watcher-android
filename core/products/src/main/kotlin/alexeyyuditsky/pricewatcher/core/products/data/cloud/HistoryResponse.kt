package alexeyyuditsky.pricewatcher.core.products.data.cloud

import com.google.gson.annotations.SerializedName

internal data class HistoryResponse(
    @field:SerializedName("points") val points: List<PricePointResponse>? = null
)