package alexeyyuditsky.pricewatcher.feature.tracking.presentation.mapper

import alexeyyuditsky.pricewatcher.core.products.domain.PricePeriod
import alexeyyuditsky.pricewatcher.core.products.domain.TrackedProduct
import alexeyyuditsky.pricewatcher.core.products.domain.resizeOzonImageUrl
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.PriceChangeUi
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.PriceDecreased
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.PriceIncreased
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.PricePeriodUi
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.PriceUnchanged
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.TrackedProductUi
import java.math.BigDecimal
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale

internal class TrackingUiFormatter(
    private val priceLocale: Locale,
    dateLocale: Locale,
    dateTimePattern: String,
    zoneId: ZoneId
) {
    private val dateTime = DateTimeFormatter.ofPattern(dateTimePattern, dateLocale).withZone(zoneId)

    fun product(product: TrackedProduct): TrackedProductUi = TrackedProductUi(
        id = product.id,
        url = product.url,
        title = product.title,
        imageUrl = product.imageUrl?.let { resizeOzonImageUrl(it, IMAGE_SIZE) },
        price = product.latestPrice?.let { price(it.amount, it.currency) },
        priceChange = product.latestPrice
            ?.let { change(it.amount, it.previousAmount, it.currency) }
            ?: PriceUnchanged,
        unitPrice = product.latestPrice?.let { latest -> latest.unitAmount?.let { price(it, latest.currency) } },
        checkedAt = product.latestPrice?.let { dateTime.format(it.checkedAt) }
    )

    // Periods come newest first, so each one is compared with the next, older period.
    fun periods(periods: List<PricePeriod>): List<PricePeriodUi> =
        periods.mapIndexed { index, period ->
            val previous = periods.getOrNull(index + 1)?.takeIf { it.currency == period.currency }
            PricePeriodUi(
                price = price(period.amount, period.currency),
                unitPrice = period.unitAmount?.let { price(it, period.currency) },
                period = period(period.from, period.to),
                priceChange = change(period.amount, previous?.amount, period.currency)
            )
        }

    private fun change(amount: BigDecimal, previous: BigDecimal?, currency: String): PriceChangeUi {
        val difference = previous?.let { amount - it } ?: return PriceUnchanged
        return when (difference.signum()) {
            1 -> PriceIncreased(difference(difference, currency))
            -1 -> PriceDecreased(difference(difference.negate(), currency))
            else -> PriceUnchanged
        }
    }

    // A whole difference is shown without kopecks to stay short next to the price: "▲ 37 ₽".
    private fun difference(amount: BigDecimal, currency: String): String =
        NumberFormat.getCurrencyInstance(priceLocale).apply {
            this.currency = Currency.getInstance(currency)
            if (amount.stripTrailingZeros().scale() <= 0) maximumFractionDigits = 0
        }.format(amount)

    private fun period(from: Instant, to: Instant): String {
        val start = dateTime.format(from)
        val end = dateTime.format(to)
        return if (start == end) start else "$start — $end"
    }

    private fun price(amount: BigDecimal, currency: String): String =
        NumberFormat.getCurrencyInstance(priceLocale).apply {
            this.currency = Currency.getInstance(currency)
        }.format(amount)

    private companion object {
        // Enough for a 112 dp image on xxxhdpi screens while staying much lighter than the original.
        const val IMAGE_SIZE = 500
    }
}