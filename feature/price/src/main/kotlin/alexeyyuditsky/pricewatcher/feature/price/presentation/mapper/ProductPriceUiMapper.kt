package alexeyyuditsky.pricewatcher.feature.price.presentation.mapper

import alexeyyuditsky.pricewatcher.core.products.domain.ProductPrice
import alexeyyuditsky.pricewatcher.feature.price.presentation.ProductPriceUi
import java.text.NumberFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale

internal class ProductPriceUiMapper(
    private val locale: Locale,
    private val zoneId: ZoneId
) {

    fun map(price: ProductPrice): ProductPriceUi {
        val amount = NumberFormat.getCurrencyInstance(locale).apply {
            currency = Currency.getInstance(price.currency)
        }.format(price.amount)
        val fetchedAt = DateTimeFormatter
            .ofPattern("dd MMMM yyyy, HH:mm:ss O", locale)
            .withZone(zoneId)
            .format(price.fetchedAt)
        return ProductPriceUi(amount = amount, fetchedAt = fetchedAt)
    }
}