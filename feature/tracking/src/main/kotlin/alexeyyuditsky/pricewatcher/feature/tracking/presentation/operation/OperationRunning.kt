package alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

internal data object OperationRunning : OperationUiState {

    override fun allowsInput(): Boolean = false

    @Composable
    override fun Show() {
        LinearProgressIndicator(modifier = Modifier.testTag("operationRunning"))
    }

    private fun readResolve(): Any = OperationRunning
}

@Preview(name = "Операция выполняется", showBackground = true)
@Composable
private fun OperationRunningPreview() {
    PricewatcherTheme {
        OperationRunning.Show()
    }
}