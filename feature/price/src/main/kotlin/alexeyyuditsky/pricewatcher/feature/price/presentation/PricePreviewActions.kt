package alexeyyuditsky.pricewatcher.feature.price.presentation

internal object PricePreviewActions : PriceActions {

    override fun changeUrl(url: String) = Unit

    override fun fetchPrice() = Unit

    override fun pasteFromClipboard(text: String) = Unit
}