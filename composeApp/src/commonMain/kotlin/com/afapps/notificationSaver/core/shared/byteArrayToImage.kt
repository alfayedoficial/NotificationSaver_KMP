package com.afapps.notificationSaver.core.shared

import androidx.compose.ui.graphics.ImageBitmap


expect class PlatformImage

expect fun byteArrayToPlatformImage(byteArray: ByteArray): PlatformImage?

expect fun PlatformImage.toImageBitmap(): ImageBitmap

