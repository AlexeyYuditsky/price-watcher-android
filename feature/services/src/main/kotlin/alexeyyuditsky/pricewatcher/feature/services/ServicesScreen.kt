package alexeyyuditsky.pricewatcher.feature.services

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.services.presentation.LogoutConfirmationDialog
import alexeyyuditsky.pricewatcher.feature.services.presentation.ServiceCard
import alexeyyuditsky.pricewatcher.feature.services.presentation.ServiceUi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val services = listOf(
    ServiceUi(R.string.service_ozon, R.drawable.ic_service_ozon, "ozon", true),
    ServiceUi(R.string.service_wildberries, R.drawable.ic_service_wildberries, "wildberries", false),
    ServiceUi(R.string.service_yandex_market, R.drawable.ic_service_yandex_market, "yandex_market", false)
)

@Composable
fun ServicesScreen(onOzonClick: () -> Unit, onLogout: () -> Unit) {
    // The screen has no ViewModel; the dialog flag is UI-only and survives rotation.
    var confirmLogout by rememberSaveable { mutableStateOf(false) }
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.services_title),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { confirmLogout = true },
                        modifier = Modifier.testTag("logout")
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_logout),
                            contentDescription = stringResource(R.string.logout)
                        )
                    }
                }
            }
            item {
                Text(
                    text = stringResource(R.string.services_description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            items(items = services, key = { it.tag }) { service ->
                ServiceCard(service = service, onClick = onOzonClick)
            }
        }
    }
    if (confirmLogout) {
        LogoutConfirmationDialog(
            onConfirm = {
                confirmLogout = false
                onLogout()
            },
            onDismiss = { confirmLogout = false }
        )
    }
}

@Preview(name = "Список сервисов", showBackground = true)
@Composable
private fun ServicesScreenPreview() {
    PricewatcherTheme {
        ServicesScreen(onOzonClick = {}, onLogout = {})
    }
}

@Preview(name = "Список сервисов · тёмная тема", showBackground = true)
@Composable
private fun ServicesScreenDarkPreview() {
    PricewatcherTheme(darkTheme = true, dynamicColor = false) {
        ServicesScreen(onOzonClick = {}, onLogout = {})
    }
}