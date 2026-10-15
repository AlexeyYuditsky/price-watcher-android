package alexeyyuditsky.pricewatcher.feature.price.presentation

internal interface PriceActions {

    fun changeUrl(url: String)

    fun fetchPrice()

    fun pasteFromClipboard(text: String)
}