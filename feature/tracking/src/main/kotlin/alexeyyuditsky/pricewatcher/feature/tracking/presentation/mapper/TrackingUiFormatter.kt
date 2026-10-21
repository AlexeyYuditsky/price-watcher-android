package alexeyyuditsky.pricewatcher.feature.tracking.presentation.mapper

import alexeyyuditsky.pricewatcher.core.products.domain.PricePeriod
import alexeyyuditsky.pricewatcher.core.products.domain.TrackedProduct
import alexeyyuditsky.pricewatcher.core.products.domain.resizeOzonImageUrl
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.PricePeriodUi
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.TrackedProductUi
import java.math.BigDecimal
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale

internal class TrackingUiFormatter(
    private val locale: Locale,
    private val zoneId: ZoneId
) {
    private val dateTime = DateTimeFormatter.ofPattern("d MMM, HH:mm", locale).withZone(zoneId)

    fun product(product: TrackedProduct): TrackedProductUi = TrackedProductUi(
        id = product.id,
        url = product.url,
        title = product.title,
        imageUrl = product.imageUrl?.let { resizeOzonImageUrl(it, IMAGE_SIZE) },
        price = product.latestPrice?.let { price(it.amount, it.currency) },
        checkedAt = product.latestPrice?.let { dateTime.format(it.checkedAt) }
    )

    fun period(period: PricePeriod): PricePeriodUi = PricePeriodUi(
        price = price(period.amount, period.currency),
        period = period(period.from, period.to),
        checks = period.checks
    )

    private fun period(from: Instant, to: Instant): String {
        val start = dateTime.format(from)
        val end = dateTime.format(to)
        return if (start == end) start else "$start — $end"
    }

    private fun price(amount: BigDecimal, currency: String): String =
        NumberFormat.getCurrencyInstance(locale).apply {
            this.currency = Currency.getInstance(currency)
        }.format(amount)

    private companion object {
        // Enough for a 112 dp image on xxxhdpi screens while staying much lighter than the original.
        const val IMAGE_SIZE = 500
    }
}