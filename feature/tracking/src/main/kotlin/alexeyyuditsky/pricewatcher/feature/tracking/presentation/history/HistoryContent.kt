package alexeyyuditsky.pricewatcher.feature.tracking.presentation.history

import alexeyyuditsky.pricewatcher.feature.tracking.R
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.PricePeriodUi
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource

internal data class HistoryContent(
    private val periods: List<PricePeriodUi>
) : HistoryUiState {

    override fun show(scope: LazyListScope, actions: HistoryActions) {
        if (periods.isEmpty()) {
            scope.item(key = "empty") {
                Text(
                    text = stringResource(R.string.history_empty),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            return
        }
        scope.itemsIndexed(items = periods, key = { index, _ -> "period_$index" }) { _, period ->
            PricePeriodRow(period = period)
        }
    }
}