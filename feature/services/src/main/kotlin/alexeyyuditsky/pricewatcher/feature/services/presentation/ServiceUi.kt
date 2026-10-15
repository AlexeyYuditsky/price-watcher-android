package alexeyyuditsky.pricewatcher.feature.services.presentation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

internal data class ServiceUi(
    @field:StringRes val name: Int,
    val badge: String,
    val accent: Color,
    val isEnabled: Boolean
)