package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

internal object TrackingListPreviewActions : TrackingListActions {

    override fun changeUrl(url: String) = Unit

    override fun pasteFromClipboard(text: String) = Unit

    override fun addProduct() = Unit

    override fun removeProduct(productId: String) = Unit

    override fun reload() = Unit
}