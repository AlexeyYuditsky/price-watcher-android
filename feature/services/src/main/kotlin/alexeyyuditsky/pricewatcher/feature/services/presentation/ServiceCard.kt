package alexeyyuditsky.pricewatcher.feature.services.presentation

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.services.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun ServiceCard(service: ServiceUi, onClick: () -> Unit) {
    Card(
        onClick = { if (service.isEnabled) onClick() },
        enabled = service.isEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("service_${service.badge}"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(service.accent.copy(alpha = if (service.isEnabled) 1f else 0.55f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = service.badge,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(service.name),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(
                        if (service.isEnabled) R.string.service_available else R.string.service_coming_soon
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(name = "Активный сервис", showBackground = true)
@Composable
private fun ServiceCardEnabledPreview() {
    PricewatcherTheme {
        ServiceCard(
            service = ServiceUi(R.string.service_ozon, "OZ", Color(0xFF005BFF), true),
            onClick = {}
        )
    }
}

@Preview(name = "Сервис скоро появится", showBackground = true)
@Composable
private fun ServiceCardDisabledPreview() {
    PricewatcherTheme {
        ServiceCard(
            service = ServiceUi(R.string.service_wildberries, "WB", Color(0xFF9B20CF), false),
            onClick = {}
        )
    }
}