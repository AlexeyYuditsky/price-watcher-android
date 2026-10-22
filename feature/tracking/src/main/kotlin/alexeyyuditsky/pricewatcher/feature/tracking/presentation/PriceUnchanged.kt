package alexeyyuditsky.pricewatcher.feature.tracking.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

// No change to show: the first known price, or a price in another currency than before.
internal data object PriceUnchanged : PriceChangeUi {

    @Composable
    override fun priceColor(): Color = Color.Unspecified

    @Composable
    override fun Show(style: TextStyle, modifier: Modifier) = Unit

    private fun readResolve(): Any = PriceUnchanged
}