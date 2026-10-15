package alexeyyuditsky.pricewatcher.feature.price.presentation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.price.R
import android.content.ClipboardManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
internal fun PriceScreen(
    state: PriceUiState,
    url: String,
    actions: PriceActions,
    onBack: () -> Unit = {}
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TextButton(onClick = onBack, modifier = Modifier.testTag("backToServices")) {
                Text(text = stringResource(R.string.back_to_services))
            }
            Text(
                text = stringResource(R.string.price_title),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(R.string.price_description),
                style = MaterialTheme.typography.bodyLarge
            )
            OutlinedTextField(
                value = url,
                onValueChange = actions::changeUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("productUrl"),
                enabled = state.allowsInput(),
                label = { Text(text = stringResource(R.string.product_url)) },
                placeholder = { Text(text = stringResource(R.string.product_url_placeholder)) },
                minLines = 2,
                maxLines = 5,
                isError = state.isInputError(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Uri
                )
            )
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
                enabled = state.allowsInput(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("pasteProductUrl")
            ) {
                Text(text = stringResource(R.string.paste_from_clipboard))
            }
            Button(
                onClick = {
                    keyboard?.hide()
                    actions.fetchPrice()
                },
                enabled = url.isNotBlank() && state.allowsInput(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("fetchPrice")
            ) {
                Text(text = stringResource(state.fetchButtonText()))
            }
            state.Show(actions)
        }
    }
}

@Preview(name = "Ввод ссылки", showBackground = true)
@Composable
private fun PriceScreenPreview() {
    PricewatcherTheme {
        PriceScreen(state = PriceInitial, url = "", actions = PricePreviewActions)
    }
}

@Preview(name = "Цена получена", showBackground = true)
@Composable
private fun PriceScreenResultPreview() {
    PricewatcherTheme {
        PriceScreen(
            state = PriceSuccess(
                ProductPriceUi(
                    amount = "354,00 ₽",
                    fetchedAt = "15 октября 2026, 15:00:00 GMT+3"
                )
            ),
            url = "https://www.ozon.ru/product/ryukzak-5677073262/",
            actions = PricePreviewActions
        )
    }
}

@Preview(name = "Загрузка", showBackground = true)
@Composable
private fun PriceScreenLoadingPreview() {
    PricewatcherTheme {
        PriceScreen(
            state = PriceLoading(),
            url = "https://ozon.ru/t/WckUwaa",
            actions = PricePreviewActions
        )
    }
}

@Preview(name = "Ошибка", showBackground = true)
@Composable
private fun PriceScreenErrorPreview() {
    PricewatcherTheme {
        PriceScreen(
            state = PriceError(R.string.error_network),
            url = "https://ozon.ru/t/WckUwaa",
            actions = PricePreviewActions
        )
    }
}

@Preview(name = "Новая ссылка во время загрузки", showBackground = true)
@Composable
private fun PriceScreenPendingSharePreview() {
    PricewatcherTheme {
        PriceScreen(
            state = PriceLoading(
                pendingSharedText = "https://www.ozon.ru/product/ryukzak-5677073262/"
            ),
            url = "https://ozon.ru/t/WckUwaa",
            actions = PricePreviewActions
        )
    }
}