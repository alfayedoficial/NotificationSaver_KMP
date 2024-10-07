package com.afapps.notificationSaver.core.shared

import platform.UIKit.UIApplication
import platform.Foundation.NSURL

actual fun openAppRatingPage(platformContext: PlatformContext) {
    val appStoreUrl = "itms-apps://itunes.apple.com/app/idYOUR_APP_ID?action=write-review"
    val nsUrl = NSURL.URLWithString(appStoreUrl)

    if (nsUrl != null && UIApplication.sharedApplication.canOpenURL(nsUrl)) {
        UIApplication.sharedApplication.openURL(nsUrl)
    }
}

actual val appStoreId: String
    get() = "YOUR_APP_ID"


actual class PlatformContext

actual fun PlatformContext.openUrl(url: String) {
    val nsUrl = NSURL.URLWithString(url)
    if (nsUrl != null && UIApplication.sharedApplication.canOpenURL(nsUrl)) {
        UIApplication.sharedApplication.openURL(nsUrl)
    }
}