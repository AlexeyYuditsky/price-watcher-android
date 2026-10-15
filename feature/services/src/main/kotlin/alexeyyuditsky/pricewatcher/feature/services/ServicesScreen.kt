package alexeyyuditsky.pricewatcher.feature.services

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.services.presentation.ServiceCard
import alexeyyuditsky.pricewatcher.feature.services.presentation.ServiceUi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val services = listOf(
    ServiceUi(R.string.service_ozon, "OZ", Color(0xFF005BFF), true),
    ServiceUi(R.string.service_wildberries, "WB", Color(0xFF9B20CF), false),
    ServiceUi(R.string.service_yandex_market, "Я", Color(0xFFD49B00), false),
    ServiceUi(R.string.service_aliexpress, "Ali", Color(0xFFE83C28), false)
)

@Composable
fun ServicesScreen(onOzonClick: () -> Unit) {
    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.services_title),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            item {
                Text(
                    text = stringResource(R.string.services_description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            items(items = services, key = { it.name }) { service ->
                ServiceCard(service = service, onClick = onOzonClick)
            }
        }
    }
}

@Preview(name = "Список сервисов", showBackground = true)
@Composable
private fun ServicesScreenPreview() {
    PricewatcherTheme {
        ServicesScreen(onOzonClick = {})
    }
}

@Preview(name = "Список сервисов · тёмная тема", showBackground = true)
@Composable
private fun ServicesScreenDarkPreview() {
    PricewatcherTheme(darkTheme = true, dynamicColor = false) {
        ServicesScreen(onOzonClick = {})
    }
}