package alexeyyuditsky.pricewatcher.core.auth.data

import alexeyyuditsky.pricewatcher.core.auth.domain.AuthorizationStatus
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
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
    @param:SessionDataStore private val dataStore: DataStore<Preferences>,
    private val cipher: SessionCipher
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
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = cipher.encrypt(session.accessToken)
            preferences[REFRESH_TOKEN_KEY] = cipher.encrypt(session.refreshToken)
        }
        publish(session)
    }

    suspend fun clear() {
        loaded.await()
        dataStore.edit { it.clear() }
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

    private suspend fun load(): StoredSession? = try {
        val preferences = dataStore.data.first()
        val access = preferences[ACCESS_TOKEN_KEY]
        val refresh = preferences[REFRESH_TOKEN_KEY]
        if (access == null || refresh == null) {
            null
        } else {
            StoredSession(cipher.decrypt(access), cipher.decrypt(refresh))
        }
    } catch (_: GeneralSecurityException) {
        dataStore.edit { it.clear() }
        null
    } catch (_: IllegalArgumentException) {
        dataStore.edit { it.clear() }
        null
    } catch (_: IOException) {
        null
    }

    private companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }
}