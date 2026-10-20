package alexeyyuditsky.pricewatcher.feature.auth.presentation

internal object AuthPreviewActions : AuthActions {

    override fun changeEmail(email: String) = Unit

    override fun changePassword(password: String) = Unit

    override fun switchMode() = Unit

    override fun submit() = Unit
}