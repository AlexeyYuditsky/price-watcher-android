package alexeyyuditsky.pricewatcher.core.products.domain

import javax.inject.Inject

class PricePeriodGrouper @Inject constructor() {

    fun group(points: List<PricePoint>): List<PricePeriod> {
        val periods = mutableListOf<PricePeriod>()
        points.sortedBy { it.checkedAt }.forEach { point ->
            val last = periods.lastOrNull()
            if (last != null && last.currency == point.currency && last.amount.compareTo(point.amount) == 0) {
                periods[periods.lastIndex] = last.copy(to = point.checkedAt, checks = last.checks + 1)
            } else {
                periods += PricePeriod(
                    from = point.checkedAt,
                    to = point.checkedAt,
                    amount = point.amount,
                    currency = point.currency,
                    checks = 1
                )
            }
        }
        return periods.asReversed()
    }
}