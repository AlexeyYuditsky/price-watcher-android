package alexeyyuditsky.pricewatcher.feature.tracking.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// No change to show: the first known price, or a price in another currency than before.
internal data object PriceUnchanged : PriceChangeUi {

    @Composable
    override fun priceColor(): Color = Color.Unspecified

    @Composable
    override fun change(): String? = null

    @Composable
    override fun description(): String? = null

    private fun readResolve(): Any = PriceUnchanged
}