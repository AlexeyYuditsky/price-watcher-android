package alexeyyuditsky.pricewatcher.feature.tracking.presentation.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

internal data object HistoryLoading : HistoryUiState {

    override fun show(scope: LazyListScope, actions: HistoryActions) {
        scope.item(key = "loading") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.testTag("historyLoading"))
            }
        }
    }

    private fun readResolve(): Any = HistoryLoading
}