package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.tracking.R
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

internal data class RemovalConfirmation(
    private val productId: String,
    private val title: String?
) : RemovalUiState {

    @Composable
    override fun Show(actions: TrackingListActions) {
        AlertDialog(
            onDismissRequest = actions::cancelRemoval,
            confirmButton = {
                TextButton(
                    onClick = actions::confirmRemoval,
                    modifier = Modifier.testTag("confirmRemoval")
                ) {
                    Text(text = stringResource(R.string.remove_product))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = actions::cancelRemoval,
                    modifier = Modifier.testTag("cancelRemoval")
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            title = { Text(text = stringResource(R.string.remove_confirm_title)) },
            text = {
                Text(
                    text = stringResource(
                        R.string.remove_confirm_text,
                        title ?: stringResource(R.string.no_title)
                    )
                )
            },
            modifier = Modifier.testTag("removalDialog")
        )
    }

    override fun confirm(remove: (productId: String) -> Unit) = remove(productId)
}

@Preview(name = "Подтверждение удаления", showBackground = true)
@Composable
private fun RemovalConfirmationPreview() {
    PricewatcherTheme {
        RemovalConfirmation(productId = "1", title = "Рюкзак городской 20 л")
            .Show(TrackingListPreviewActions)
    }
}