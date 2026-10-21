package alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation

import androidx.compose.runtime.Composable
import java.io.Serializable

internal sealed interface OperationUiState : Serializable {

    @Composable
    fun Show()

    fun allowsInput(): Boolean = true

    fun reloadsData(): Boolean = false

    fun savedProductId(): String? = null
}