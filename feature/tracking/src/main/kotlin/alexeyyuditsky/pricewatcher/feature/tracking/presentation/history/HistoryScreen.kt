package alexeyyuditsky.pricewatcher.feature.tracking.presentation.history

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.feature.tracking.R
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.PricePeriodUi
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationIdle
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationRunning
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationUiState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun HistoryScreen(
    title: String?,
    state: HistoryUiState,
    operation: OperationUiState,
    actions: HistoryActions,
    onBack: () -> Unit
) {
    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(key = "header") {
                TextButton(onClick = onBack, modifier = Modifier.testTag("backToTracking")) {
                    Text(text = stringResource(R.string.back_to_tracking))
                }
                Text(
                    text = stringResource(R.string.history_title),
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = title ?: stringResource(R.string.no_title),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            item(key = "refresh") {
                Button(
                    onClick = actions::refresh,
                    enabled = operation.allowsInput(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("refreshProduct")
                ) {
                    Text(
                        text = stringResource(
                            if (operation.allowsInput()) R.string.refresh_now else R.string.refreshing
                        )
                    )
                }
            }
            item(key = "operation") {
                operation.Show()
            }
            state.show(this, actions)
        }
    }
}

@Preview(name = "История цены", showBackground = true)
@Composable
private fun HistoryScreenPreview() {
    PricewatcherTheme {
        HistoryScreen(
            title = "Рюкзак городской",
            state = HistoryContent(
                listOf(
                    PricePeriodUi("353,00 ₽", "20 окт., 12:00 — 20 окт., 16:30", 10),
                    PricePeriodUi("375,00 ₽", "19 окт., 10:00 — 20 окт., 11:30", 52)
                )
            ),
            operation = OperationIdle,
            actions = HistoryPreviewActions,
            onBack = {}
        )
    }
}

@Preview(name = "История · обновление", showBackground = true)
@Composable
private fun HistoryScreenRefreshingPreview() {
    PricewatcherTheme {
        HistoryScreen(
            title = null,
            state = HistoryLoading,
            operation = OperationRunning,
            actions = HistoryPreviewActions,
            onBack = {}
        )
    }
}

@Preview(name = "История · ошибка", showBackground = true)
@Composable
private fun HistoryScreenErrorPreview() {
    PricewatcherTheme {
        HistoryScreen(
            title = "Рюкзак городской",
            state = HistoryError(R.string.error_network),
            operation = OperationIdle,
            actions = HistoryPreviewActions,
            onBack = {}
        )
    }
}