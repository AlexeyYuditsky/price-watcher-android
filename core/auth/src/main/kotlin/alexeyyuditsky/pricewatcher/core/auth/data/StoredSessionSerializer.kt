package alexeyyuditsky.pricewatcher.core.auth.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

// Plain JSON of the session; encryption is added by wrapping it into AeadSerializer.
internal object StoredSessionSerializer : Serializer<StoredSession?> {

    override val defaultValue: StoredSession? = null

    override suspend fun readFrom(input: InputStream): StoredSession? {
        val bytes = input.readBytes()
        if (bytes.isEmpty()) return null
        return try {
            Json.decodeFromString<StoredSession?>(bytes.decodeToString())
        } catch (e: SerializationException) {
            throw CorruptionException("Cannot read stored session", e)
        }
    }

    override suspend fun writeTo(t: StoredSession?, output: OutputStream) {
        output.write(Json.encodeToString(t).encodeToByteArray())
    }
}