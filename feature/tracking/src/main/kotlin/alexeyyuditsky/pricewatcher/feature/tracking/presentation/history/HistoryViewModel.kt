package alexeyyuditsky.pricewatcher.feature.tracking.presentation.history

import alexeyyuditsky.pricewatcher.core.common.RunAsync
import alexeyyuditsky.pricewatcher.core.products.domain.GetPriceHistoryUseCase
import alexeyyuditsky.pricewatcher.core.products.domain.RefreshProductUseCase
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationIdle
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationResultUiMapper
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationRunning
import alexeyyuditsky.pricewatcher.feature.tracking.presentation.operation.OperationUiState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
internal class HistoryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getHistory: GetPriceHistoryUseCase,
    private val refreshProduct: RefreshProductUseCase,
    private val runAsync: RunAsync,
    private val historyMapper: HistoryResultUiMapper,
    private val operationMapper: OperationResultUiMapper
) : ViewModel(), HistoryActions {

    val state: StateFlow<HistoryUiState> = savedStateHandle.getStateFlow(STATE_KEY, HistoryLoading)
    val operation: StateFlow<OperationUiState> =
        savedStateHandle.getStateFlow(OPERATION_KEY, OperationIdle)

    init {
        if (!operation.value.allowsInput()) updateOperation(OperationIdle)
    }

    fun open(productId: String) {
        if (savedStateHandle.get<String>(PRODUCT_ID_KEY) != productId) {
            savedStateHandle[PRODUCT_ID_KEY] = productId
            savedStateHandle[STATE_KEY] = HistoryLoading
            updateOperation(OperationIdle)
        }
        reload()
    }

    override fun reload() {
        val productId = savedStateHandle.get<String>(PRODUCT_ID_KEY) ?: return
        runAsync.runAsync(
            scope = viewModelScope,
            background = { getHistory(productId).map(historyMapper) }
        ) { result ->
            savedStateHandle[STATE_KEY] = result.getOrElse { historyMapper.mapInvalidResponse() }
        }
    }

    override fun refresh() {
        val productId = savedStateHandle.get<String>(PRODUCT_ID_KEY) ?: return
        if (!operation.value.allowsInput()) return
        updateOperation(OperationRunning)
        runAsync.runAsync(
            scope = viewModelScope,
            background = { refreshProduct(productId).map(operationMapper) }
        ) { result ->
            val next = result.getOrElse { operationMapper.mapInvalidResponse() }
            if (next.reloadsData()) reload()
            updateOperation(next)
        }
    }

    private fun updateOperation(state: OperationUiState) {
        savedStateHandle[OPERATION_KEY] = state
    }

    companion object {
        private const val STATE_KEY = "history_state"
        private const val OPERATION_KEY = "history_operation"
        private const val PRODUCT_ID_KEY = "history_product_id"
    }
}