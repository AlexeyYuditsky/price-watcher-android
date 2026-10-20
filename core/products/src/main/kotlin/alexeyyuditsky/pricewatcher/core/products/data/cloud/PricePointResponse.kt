package alexeyyuditsky.pricewatcher.core.products.data.cloud

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

internal data class PricePointResponse(
    @field:SerializedName("checkedAt") val checkedAt: String? = null,
    @field:SerializedName("amount") val amount: BigDecimal? = null,
    @field:SerializedName("currency") val currency: String? = null
)