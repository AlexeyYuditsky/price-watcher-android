package alexeyyuditsky.pricewatcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import alexeyyuditsky.pricewatcher.ui.price.PriceScreen
import alexeyyuditsky.pricewatcher.ui.price.PriceViewModel
import alexeyyuditsky.pricewatcher.ui.theme.PricewatcherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PricewatcherTheme {
                val viewModel: PriceViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                PriceScreen(
                    state = state,
                    onUrlChanged = viewModel::onUrlChanged,
                    onFetchPrice = viewModel::fetchPrice,
                    onPasteFromClipboard = viewModel::pasteFromClipboard
                )
            }
        }
    }
}