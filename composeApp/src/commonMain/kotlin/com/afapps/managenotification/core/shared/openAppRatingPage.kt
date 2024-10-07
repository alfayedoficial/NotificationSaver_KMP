package com.afapps.managenotification.core.shared


expect class PlatformContext

expect val appStoreId: String

expect fun openAppRatingPage(platformContext: PlatformContext)


