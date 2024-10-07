package com.afapps.notificationSaver.core.shared


expect class PlatformContext

expect val appStoreId: String

expect fun openAppRatingPage(platformContext: PlatformContext)

expect fun PlatformContext.openUrl(url: String)


