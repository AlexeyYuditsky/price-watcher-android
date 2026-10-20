package alexeyyuditsky.pricewatcher.feature.auth.presentation

internal interface AuthActions {

    fun changeEmail(email: String)

    fun changePassword(password: String)

    fun switchMode()

    fun submit()
}