package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.tracking.R
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.TrackedProductUi
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationFailed
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationIdle
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationRunning
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationUiState
import android.content.ClipboardManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun TrackingListScreen(
    state: TrackingListUiState,
    operation: OperationUiState,
    url: String,
    actions: TrackingListActions,
    onBack: () -> Unit,
    onOpenProduct: (productId: String, title: String?) -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding(),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "header") {
                TextButton(onClick = onBack, modifier = Modifier.testTag("backToServices")) {
                    Text(text = stringResource(R.string.back_to_services))
                }
                Text(
                    text = stringResource(R.string.tracking_title),
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = stringResource(R.string.tracking_description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            item(key = "input") {
                OutlinedTextField(
                    value = url,
                    onValueChange = actions::changeUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("productUrl"),
                    enabled = operation.allowsInput(),
                    label = { Text(text = stringResource(R.string.product_url)) },
                    placeholder = { Text(text = stringResource(R.string.product_url_placeholder)) },
                    maxLines = 3,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Uri
                    )
                )
            }
            item(key = "buttons") {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = {
                            val text = runCatching {
                                val clip = context.getSystemService(ClipboardManager::class.java)?.primaryClip
                                clip?.takeIf { it.itemCount > 0 }
                                    ?.getItemAt(0)
                                    ?.coerceToText(context)
                                    ?.toString()
                            }.getOrNull().orEmpty()
                            actions.pasteFromClipboard(text)
                        },
                        enabled = operation.allowsInput(),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("pasteProductUrl")
                    ) {
                        Text(text = stringResource(R.string.paste_from_clipboard))
                    }
                    Button(
                        onClick = {
                            keyboard?.hide()
                            actions.addProduct()
                        },
                        enabled = url.isNotBlank() && operation.allowsInput(),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("addProduct")
                    ) {
                        Text(
                            text = stringResource(
                                if (operation.allowsInput()) R.string.add_product else R.string.adding_product
                            )
                        )
                    }
                }
            }
            item(key = "operation") {
                operation.Show()
            }
            state.show(this, actions, onOpenProduct)
        }
    }
}

@Preview(name = "Список отслеживания", showBackground = true)
@Composable
private fun TrackingListScreenPreview() {
    PricewatcherTheme {
        TrackingListScreen(
            state = TrackingListContent(
                listOf(
                    TrackedProductUi("1", "Рюкзак городской", null, "353,00 ₽", "20 окт., 16:30"),
                    TrackedProductUi("2", "Смартфон OnePlus 16", null, "54 990,00 ₽", "20 окт., 16:00")
                )
            ),
            operation = OperationIdle,
            url = "",
            actions = TrackingListPreviewActions,
            onBack = {},
            onOpenProduct = { _, _ -> }
        )
    }
}

@Preview(name = "Список пуст", showBackground = true)
@Composable
private fun TrackingListScreenEmptyPreview() {
    PricewatcherTheme {
        TrackingListScreen(
            state = TrackingListContent(emptyList()),
            operation = OperationIdle,
            url = "",
            actions = TrackingListPreviewActions,
            onBack = {},
            onOpenProduct = { _, _ -> }
        )
    }
}

@Preview(name = "Добавление товара", showBackground = true)
@Composable
private fun TrackingListScreenAddingPreview() {
    PricewatcherTheme {
        TrackingListScreen(
            state = TrackingListLoading,
            operation = OperationRunning,
            url = "https://ozon.ru/t/WckUwaa",
            actions = TrackingListPreviewActions,
            onBack = {},
            onOpenProduct = { _, _ -> }
        )
    }
}

@Preview(name = "Ошибка", showBackground = true)
@Composable
private fun TrackingListScreenErrorPreview() {
    PricewatcherTheme {
        TrackingListScreen(
            state = TrackingListError(R.string.error_network),
            operation = OperationFailed(R.string.error_invalid_url),
            url = "https://example.com",
            actions = TrackingListPreviewActions,
            onBack = {},
            onOpenProduct = { _, _ -> }
        )
    }
}