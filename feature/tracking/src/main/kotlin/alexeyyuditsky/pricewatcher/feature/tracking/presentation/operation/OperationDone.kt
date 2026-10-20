package alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation

import androidx.compose.runtime.Composable

internal data object OperationDone : OperationUiState {

    override fun reloadsData(): Boolean = true

    @Composable
    override fun Show() = Unit

    private fun readResolve(): Any = OperationDone
}