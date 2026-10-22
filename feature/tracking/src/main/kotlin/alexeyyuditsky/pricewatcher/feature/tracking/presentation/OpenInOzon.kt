package alexeyyuditsky.pricewatcher.feature.tracking.presentation

import alexeyyuditsky.pricewatcher.feature.tracking.R
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

// Opens the product right in the Ozon app when it is installed, otherwise in any app for the link.
internal fun Context.openInOzon(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).addCategory(Intent.CATEGORY_BROWSABLE)
    try {
        // A fresh Ozon task holds only this product, without the screens left from earlier Ozon use,
        // so Back from the product returns here instead of walking through Ozon.
        startActivity(
            Intent(intent)
                .setPackage(OZON_PACKAGE)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    } catch (_: ActivityNotFoundException) {
        try {
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(this, R.string.error_open_ozon, Toast.LENGTH_SHORT).show()
        }
    }
}

private const val OZON_PACKAGE = "ru.ozon.app.android"