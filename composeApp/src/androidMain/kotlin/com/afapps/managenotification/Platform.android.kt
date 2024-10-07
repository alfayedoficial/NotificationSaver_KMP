package com.afapps.managenotification

import android.os.Build
import com.afapps.managenotification.presentation.Platform


class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}


