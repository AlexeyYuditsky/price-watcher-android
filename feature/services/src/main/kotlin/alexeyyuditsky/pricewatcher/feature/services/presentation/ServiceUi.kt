package alexeyyuditsky.pricewatcher.feature.services.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal data class ServiceUi(
    @field:StringRes val name: Int,
    @field:DrawableRes val icon: Int,
    val tag: String,
    val isEnabled: Boolean
)