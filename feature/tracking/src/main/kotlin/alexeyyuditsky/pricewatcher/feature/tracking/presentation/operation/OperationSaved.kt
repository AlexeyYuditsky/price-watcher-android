package alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation

import androidx.compose.runtime.Composable

internal data class OperationSaved(
    private val productId: String
) : OperationUiState {

    override fun reloadsData(): Boolean = true

    override fun savedProductId(): String = productId

    @Composable
    override fun Show() = Unit
}