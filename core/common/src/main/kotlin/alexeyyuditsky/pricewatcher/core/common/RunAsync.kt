package alexeyyuditsky.pricewatcher.core.common

import kotlinx.coroutines.CoroutineScope

interface RunAsync {

    fun <T> runAsync(
        scope: CoroutineScope,
        background: suspend () -> T,
        ui: (Result<T>) -> Unit
    )
}