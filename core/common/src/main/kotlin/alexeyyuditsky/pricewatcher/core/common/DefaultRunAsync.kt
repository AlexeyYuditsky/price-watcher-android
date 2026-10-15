package alexeyyuditsky.pricewatcher.core.common

import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class DefaultRunAsync @Inject constructor(
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) : RunAsync {

    override fun <T> runAsync(
        scope: CoroutineScope,
        background: suspend () -> T,
        ui: (Result<T>) -> Unit
    ) {
        scope.launch {
            val result = try {
                Result.success(withContext(dispatcher) { background() })
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                Result.failure(error)
            }
            ui(result)
        }
    }
}