package com.afapps.notificationSaver

import android.os.Build
import com.afapps.notificationSaver.presentation.Platform


class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}


