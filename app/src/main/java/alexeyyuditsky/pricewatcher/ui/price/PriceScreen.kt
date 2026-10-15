package alexeyyuditsky.pricewatcher.ui.price

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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import alexeyyuditsky.pricewatcher.R
import alexeyyuditsky.pricewatcher.domain.ProductPrice
import alexeyyuditsky.pricewatcher.ui.theme.PricewatcherTheme
import java.math.BigDecimal
import java.text.NumberFormat
import java.time.ZoneId
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale

@Composable
fun PriceScreen(
    state: PriceUiState,
    onUrlChanged: (String) -> Unit,
    onFetchPrice: () -> Unit,
    onPasteFromClipboard: (String) -> Unit = {}
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(stringResource(R.string.price_title), style = MaterialTheme.typography.headlineMedium)
            Text(stringResource(R.string.price_description), style = MaterialTheme.typography.bodyLarge)
            OutlinedTextField(
                value = state.url,
                onValueChange = onUrlChanged,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading,
                label = { Text(stringResource(R.string.product_url)) },
                placeholder = { Text("https://www.ozon.ru/product/…") },
                minLines = 2,
                maxLines = 5,
                isError = state.error == R.string.error_invalid_url,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Uri
                )
            )
            OutlinedButton(
                onClick = {
                    val text = runCatching {
                        val clip = context.getSystemService(ClipboardManager::class.java)?.primaryClip
                        clip?.takeIf { it.itemCount > 0 }?.getItemAt(0)?.coerceToText(context)?.toString()
                    }.getOrNull().orEmpty()
                    onPasteFromClipboard(text)
                },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.paste_from_clipboard))
            }
            Button(
                onClick = {
                    keyboard?.hide()
                    onFetchPrice()
                },
                enabled = state.url.isNotBlank() && !state.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(if (state.isLoading) R.string.fetching_price else R.string.fetch_price))
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            state.error?.let { error ->
                Text(stringResource(error), color = MaterialTheme.colorScheme.error)
            }
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(stringResource(R.string.price_label), style = MaterialTheme.typography.labelLarge)
                    val price = state.price
                    val amount = price?.let {
                        NumberFormat.getCurrencyInstance(Locale.forLanguageTag("ru-RU")).apply {
                            currency = Currency.getInstance(it.currency)
                        }.format(it.amount)
                    } ?: stringResource(R.string.no_price)
                    Text(amount, style = MaterialTheme.typography.headlineLarge)
                    Text(stringResource(R.string.fetched_at_label), style = MaterialTheme.typography.labelLarge)
                    val date = price?.let {
                        DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm:ss O", Locale.forLanguageTag("ru-RU"))
                            .withZone(ZoneId.systemDefault())
                            .format(it.fetchedAt)
                    } ?: stringResource(R.string.no_date)
                    Text(date, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Preview(name = "Ввод ссылки", showBackground = true)
@Composable
private fun PriceScreenPreview() {
    PricewatcherTheme {
        PriceScreen(state = PriceUiState(), onUrlChanged = {}, onFetchPrice = {})
    }
}

@Preview(name = "Цена получена", showBackground = true)
@Composable
private fun PriceScreenResultPreview() {
    PricewatcherTheme {
        PriceScreen(
            state = PriceUiState(
                url = "https://www.ozon.ru/product/ryukzak-5677073262/",
                price = ProductPrice(
                    amount = BigDecimal("354.00"),
                    currency = "RUB",
                    fetchedAt = Instant.parse("2026-10-15T12:00:00Z")
                )
            ),
            onUrlChanged = {},
            onFetchPrice = {}
        )
    }
}

@Preview(name = "Короткая ссылка Ozon", showBackground = true)
@Composable
private fun PriceScreenShortUrlPreview() {
    PricewatcherTheme {
        PriceScreen(
            state = PriceUiState(url = "https://ozon.ru/t/WckUwaa"),
            onUrlChanged = {},
            onFetchPrice = {}
        )
    }
}