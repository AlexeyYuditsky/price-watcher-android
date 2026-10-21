package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

internal object TrackingListPreviewActions : TrackingListActions {

    override fun changeUrl(url: String) = Unit

    override fun pasteFromClipboard(text: String) = Unit

    override fun addProduct() = Unit

    override fun requestRemoval(productId: String, title: String?) = Unit

    override fun confirmRemoval() = Unit

    override fun cancelRemoval() = Unit

    override fun highlightShown() = Unit

    override fun reload() = Unit
}