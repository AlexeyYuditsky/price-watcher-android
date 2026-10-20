package alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.tracking.R
import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

internal data class OperationFailed(
    @field:StringRes private val message: Int
) : OperationUiState {

    @Composable
    override fun Show() {
        Text(
            text = stringResource(message),
            modifier = Modifier.testTag("operationError"),
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview(name = "Ошибка операции", showBackground = true)
@Composable
private fun OperationFailedPreview() {
    PricewatcherTheme {
        OperationFailed(R.string.error_product_unavailable).Show()
    }
}