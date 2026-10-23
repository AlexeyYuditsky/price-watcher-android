package alexeyyuditsky.pricewatcher.feature.tracking.presentation

import alexeyyuditsky.pricewatcher.feature.tracking.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource

internal data class PriceIncreased(
    private val difference: String
) : PriceChangeUi {

    @Composable
    override fun priceColor(): Color = colorResource(R.color.price_increase)

    @Composable
    override fun change(): String = stringResource(R.string.price_increased, difference)

    @Composable
    override fun description(): String = stringResource(R.string.price_increased_description, difference)
}