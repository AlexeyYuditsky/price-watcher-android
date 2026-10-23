package alexeyyuditsky.pricewatcher.feature.tracking.presentation

import alexeyyuditsky.pricewatcher.feature.tracking.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource

internal data class PriceDecreased(
    private val difference: String
) : PriceChangeUi {

    @Composable
    override fun priceColor(): Color = colorResource(R.color.price_decrease)

    @Composable
    override fun change(): String = stringResource(R.string.price_decreased, difference)

    @Composable
    override fun description(): String = stringResource(R.string.price_decreased_description, difference)
}