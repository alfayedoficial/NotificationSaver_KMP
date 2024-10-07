package com.afapps.managenotification.core.shared

import android.content.Context
import android.content.Intent
import android.net.Uri

actual class PlatformContext(val context: Context)

actual val appStoreId: String
    get() = "YOUR_APP_ID"

actual fun openAppRatingPage(platformContext: PlatformContext) {
    val context = platformContext.context // Access the Android Context

    val uri = Uri.parse("market://details?id=${context.packageName}")
    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    try {
        context.startActivity(goToMarket)
    } catch (e: Exception) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
}



