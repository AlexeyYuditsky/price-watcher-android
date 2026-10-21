package alexeyyuditsky.pricewatcher.navigation

internal data class HistoryDestination(
    val productId: String,
    val title: String?,
    val url: String
) : AppDestination