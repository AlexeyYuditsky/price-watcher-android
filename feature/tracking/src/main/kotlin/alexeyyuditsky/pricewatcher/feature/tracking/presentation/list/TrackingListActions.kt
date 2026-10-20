package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

internal interface TrackingListActions {

    fun changeUrl(url: String)

    fun pasteFromClipboard(text: String)

    fun addProduct()

    fun removeProduct(productId: String)

    fun reload()
}