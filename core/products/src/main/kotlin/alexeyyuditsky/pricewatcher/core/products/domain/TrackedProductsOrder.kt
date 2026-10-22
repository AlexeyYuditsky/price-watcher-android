package alexeyyuditsky.pricewatcher.core.products.domain

// Products whose price dropped come first, the biggest drop on top; then those whose price rose; then the
// products without a change. The last two groups keep their original order.
fun List<TrackedProduct>.priceDropsFirst(): List<TrackedProduct> =
    sortedWith(
        compareBy<TrackedProduct> { product ->
            when {
                product.priceDrop() != null -> DROPPED
                product.priceRise() != null -> ROSE
                else -> UNCHANGED
            }
        }.thenByDescending { it.priceDrop() }
    )

private const val DROPPED = 0
private const val ROSE = 1
private const val UNCHANGED = 2