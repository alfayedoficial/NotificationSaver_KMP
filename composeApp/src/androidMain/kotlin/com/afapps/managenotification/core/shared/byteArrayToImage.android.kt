package com.afapps.managenotification.core.shared

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

// Map PlatformImage to Bitmap for Android
actual typealias PlatformImage = Bitmap

// Implement the actual function for Android
actual fun byteArrayToPlatformImage(byteArray: ByteArray): PlatformImage? {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

actual fun PlatformImage.toImageBitmap(): ImageBitmap {
    return this.asImageBitmap()
}