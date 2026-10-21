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
        startActivity(Intent(intent).setPackage(OZON_PACKAGE))
    } catch (_: ActivityNotFoundException) {
        try {
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(this, R.string.error_open_ozon, Toast.LENGTH_SHORT).show()
        }
    }
}

private const val OZON_PACKAGE = "ru.ozon.app.android"