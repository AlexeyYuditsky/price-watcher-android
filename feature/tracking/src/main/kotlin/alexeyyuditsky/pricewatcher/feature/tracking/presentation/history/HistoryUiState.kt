package alexeyyuditsky.pricewatcher.feature.tracking.presentation.history

import androidx.compose.foundation.lazy.LazyListScope
import java.io.Serializable

internal sealed interface HistoryUiState : Serializable {

    fun show(scope: LazyListScope, actions: HistoryActions)
}