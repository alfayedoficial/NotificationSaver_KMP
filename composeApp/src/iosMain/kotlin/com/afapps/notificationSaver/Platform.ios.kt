package com.afapps.notificationSaver

import com.afapps.notificationSaver.presentation.Platform
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

