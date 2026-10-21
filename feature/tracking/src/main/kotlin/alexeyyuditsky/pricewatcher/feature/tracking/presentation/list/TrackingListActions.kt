package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

internal interface TrackingListActions {

    fun changeUrl(url: String)

    fun pasteFromClipboard(text: String)

    fun addProduct()

    fun requestRemoval(productId: String, title: String?)

    fun confirmRemoval()

    fun cancelRemoval()

    fun highlightShown()

    fun reload()
}