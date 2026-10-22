package alexeyyuditsky.pricewatcher.feature.tracking.presentation.list

import alexeyyuditsky.pricewatcher.feature.tracking.R
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationUiState
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

internal data class TrackingListError(
    @field:StringRes private val message: Int
) : TrackingListUiState {

    override fun show(
        scope: LazyListScope,
        actions: TrackingListActions,
        operation: OperationUiState,
        highlightedProductId: String?,
        onOpenProduct: (productId: String, title: String?, url: String) -> Unit
    ) {
        scope.item(key = "error") {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // The same error from a failed add or removal is already shown above; only the retry remains.
                if (!operation.showsError(message)) {
                    Text(
                        text = stringResource(message),
                        modifier = Modifier.testTag("trackingError"),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                OutlinedButton(onClick = actions::reload) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        }
    }
}