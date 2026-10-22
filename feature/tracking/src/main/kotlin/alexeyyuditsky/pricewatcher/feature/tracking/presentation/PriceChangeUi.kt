package alexeyyuditsky.pricewatcher.feature.tracking.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import java.io.Serializable

// How the price moved at its latest change.
internal sealed interface PriceChangeUi : Serializable {

    // The color of the price itself; Color.Unspecified keeps the text's usual color.
    @Composable
    fun priceColor(): Color

    // The arrow with the difference next to the price. It stays on one line and shrinks down to
    // PRICE_CHANGE_MIN_FONT_SIZE when the place is narrow, because a cut number would mislead.
    @Composable
    fun Show(style: TextStyle, modifier: Modifier = Modifier)
}

internal val PRICE_CHANGE_MIN_FONT_SIZE = 10.sp