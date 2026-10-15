package alexeyyuditsky.pricewatcher

import alexeyyuditsky.pricewatcher.core.designsystem.theme.PricewatcherTheme
import alexeyyuditsky.pricewatcher.navigation.AppContent
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var sharedText: String? by mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (savedInstanceState == null) {
            receiveSharedText(intent)
        }
        setContent {
            PricewatcherTheme {
                AppContent(
                    sharedText = sharedText,
                    onSharedTextConsumed = { sharedText = null }
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        receiveSharedText(intent)
    }

    private fun receiveSharedText(intent: Intent?) {
        if (intent?.action != Intent.ACTION_SEND || intent.type != "text/plain") return
        if (!intent.hasExtra(Intent.EXTRA_TEXT)) return
        sharedText = intent.getCharSequenceExtra(Intent.EXTRA_TEXT)?.toString().orEmpty()
        intent.removeExtra(Intent.EXTRA_TEXT)
    }
}