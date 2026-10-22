package alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import java.io.Serializable

internal sealed interface OperationUiState : Serializable {

    @Composable
    fun Show()

    fun allowsInput(): Boolean = true

    fun reloadsData(): Boolean = false

    fun savedProductId(): String? = null

    // Lets another part of the screen skip an error message this state already shows.
    fun showsError(@StringRes message: Int): Boolean = false
}