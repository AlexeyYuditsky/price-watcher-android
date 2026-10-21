package alexeyyuditsky.pricewatcher.feature.services.presentation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.services.R
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun LogoutConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm, modifier = Modifier.testTag("confirmLogout")) {
                Text(text = stringResource(R.string.logout_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, modifier = Modifier.testTag("cancelLogout")) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        title = { Text(text = stringResource(R.string.logout_confirm_title)) },
        text = { Text(text = stringResource(R.string.logout_confirm_text)) },
        modifier = Modifier.testTag("logoutDialog")
    )
}

@Preview(name = "Подтверждение выхода", showBackground = true)
@Composable
private fun LogoutConfirmationDialogPreview() {
    PricewatcherTheme {
        LogoutConfirmationDialog(onConfirm = {}, onDismiss = {})
    }
}