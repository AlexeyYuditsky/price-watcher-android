package alexeyyuditsky.pricewatcher.core.auth.data

import alexeyyuditsky.pricewatcher.core.auth.domain.AuthorizationStatus
import androidx.datastore.core.DataStore
import dagger.Lazy
import java.io.IOException
import java.security.GeneralSecurityException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Singleton
internal class SessionStorage @Inject constructor(
    // Lazy: creating the encrypted DataStore touches the Android Keystore, so it happens in init on IO.
    @param:SessionDataStore private val dataStore: Lazy<DataStore<StoredSession?>>
) {

    private val loaded = CompletableDeferred<Unit>()
    @Volatile
    private var cached: StoredSession? = null
    private val status = MutableStateFlow(AuthorizationStatus.CHECKING)

    val authorizationStatus: StateFlow<AuthorizationStatus> = status.asStateFlow()

    init {
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            publish(load())
            loaded.complete(Unit)
        }
    }

    suspend fun read(): StoredSession? {
        loaded.await()
        return cached
    }

    suspend fun save(session: StoredSession) {
        loaded.await()
        dataStore.get().updateData { session }
        publish(session)
    }

    suspend fun clear() {
        loaded.await()
        dataStore.get().updateData { null }
        publish(null)
    }

    private fun publish(session: StoredSession?) {
        cached = session
        status.value = if (session == null) {
            AuthorizationStatus.UNAUTHORIZED
        } else {
            AuthorizationStatus.AUTHORIZED
        }
    }

    // Undecryptable data is replaced by the DataStore corruption handler; here only the keyset can fail.
    private suspend fun load(): StoredSession? = try {
        dataStore.get().data.first()
    } catch (_: GeneralSecurityException) {
        null
    } catch (_: IOException) {
        null
    }
}