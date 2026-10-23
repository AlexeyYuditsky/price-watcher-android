package alexeyyuditsky.pricewatcher.core.auth.di

import alexeyyuditsky.pricewatcher.core.auth.data.SessionDataStore
import alexeyyuditsky.pricewatcher.core.auth.data.StoredSession
import alexeyyuditsky.pricewatcher.core.auth.data.StoredSessionSerializer
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import androidx.datastore.tink.AeadSerializer
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.RegistryConfiguration
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
internal object SessionDataStoreModule {

    private const val SESSION_FILE = "session"
    private const val KEYSET_NAME = "session_keyset"
    private const val KEYSET_PREFS = "session_keyset_prefs"
    private const val MASTER_KEY_URI = "android-keystore://price_watcher_session_master_key"

    // Reads the keyset and the Android Keystore, so SessionStorage obtains it lazily off the main thread.
    @Provides
    @Singleton
    @SessionDataStore
    fun provideSessionDataStore(@ApplicationContext context: Context): DataStore<StoredSession?> =
        DataStoreFactory.create(
            serializer = AeadSerializer(
                aead = sessionAead(context),
                wrappedSerializer = StoredSessionSerializer,
                // Binds the ciphertext to this file so it cannot be swapped with another one.
                associatedData = SESSION_FILE.encodeToByteArray()
            ),
            // A session that cannot be decrypted (e.g. the Keystore key is lost) means signing in again.
            corruptionHandler = ReplaceFileCorruptionHandler { null },
            scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
            produceFile = { context.dataStoreFile(SESSION_FILE) }
        )

    private fun sessionAead(context: Context): Aead {
        AeadConfig.register()
        return AndroidKeysetManager.Builder()
            .withSharedPref(context, KEYSET_NAME, KEYSET_PREFS)
            .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
            .keysetHandle
            .getPrimitive(RegistryConfiguration.get(), Aead::class.java)
    }
}