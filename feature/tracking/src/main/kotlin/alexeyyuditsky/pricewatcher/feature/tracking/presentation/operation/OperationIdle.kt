package alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation

import androidx.compose.runtime.Composable

internal data object OperationIdle : OperationUiState {

    @Composable
    override fun Show() = Unit

    private fun readResolve(): Any = OperationIdle
}