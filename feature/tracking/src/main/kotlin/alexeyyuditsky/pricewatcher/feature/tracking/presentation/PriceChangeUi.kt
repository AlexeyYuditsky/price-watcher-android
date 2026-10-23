package alexeyyuditsky.pricewatcher.feature.tracking.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import java.io.Serializable

// How the price moved at its latest change.
internal sealed interface PriceChangeUi : Serializable {

    // The color of the price and its change; Color.Unspecified keeps the text's usual color.
    @Composable
    fun priceColor(): Color

    // The arrow with the difference shown right after the price, or null when there is nothing to show.
    @Composable
    fun change(): String?

    // What a screen reader says about the change, or null when there is nothing to say.
    @Composable
    fun description(): String?
}