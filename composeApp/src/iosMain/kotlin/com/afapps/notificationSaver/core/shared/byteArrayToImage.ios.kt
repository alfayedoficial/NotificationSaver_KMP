package com.afapps.notificationSaver.core.shared

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.UIKit.UIImage
import platform.Foundation.NSData
import platform.Foundation.create

actual typealias PlatformImage = UIImage

@OptIn(ExperimentalForeignApi::class)
actual fun byteArrayToPlatformImage(byteArray: ByteArray): PlatformImage? {
    // Convert ByteArray to NSData using usePinned to get the address
    val data = byteArray.usePinned { pinnedByteArray ->
        NSData.create(bytes = pinnedByteArray.addressOf(0), length = byteArray.size.toULong())
    }

    // Use NSData to create a UIImage
    return UIImage(data = data)
}

// Placeholder or a custom iOS handling
actual fun PlatformImage.toImageBitmap(): ImageBitmap {
    // Since Compose for iOS is not available, you can handle this differently or return a placeholder.
    // Here we return a placeholder ImageBitmap as an example.
    return ImageBitmap(40, 40) // Placeholder implementation
}