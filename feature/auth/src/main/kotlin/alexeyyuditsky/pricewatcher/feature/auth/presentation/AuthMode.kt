package alexeyyuditsky.pricewatcher.feature.auth.presentation

import alexeyyuditsky.pricewatcher.feature.auth.R
import androidx.annotation.StringRes

internal enum class AuthMode(
    @field:StringRes val title: Int,
    @field:StringRes val submitText: Int,
    @field:StringRes val switchText: Int
) {
    LOGIN(R.string.login_title, R.string.login_button, R.string.switch_to_register) {
        override fun toggle(): AuthMode = REGISTER
    },
    REGISTER(R.string.register_title, R.string.register_button, R.string.switch_to_login) {
        override fun toggle(): AuthMode = LOGIN
    };

    abstract fun toggle(): AuthMode
}