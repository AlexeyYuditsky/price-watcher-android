package alexeyyuditsky.pricewatcher.feature.tracking.presentation.history

import alexeyyuditsky.pricewatcher.feature.tracking.R
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

internal data class HistoryError(
    @field:StringRes private val message: Int
) : HistoryUiState {

    override fun show(scope: LazyListScope, actions: HistoryActions) {
        scope.item(key = "error") {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = stringResource(message),
                    modifier = Modifier.testTag("historyError"),
                    color = MaterialTheme.colorScheme.error
                )
                OutlinedButton(onClick = actions::reload) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        }
    }
}