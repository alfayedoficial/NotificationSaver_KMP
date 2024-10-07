package com.afapps.notificationSaver.core.utilities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.afapps.notificationSaver.core.shared.byteArrayToPlatformImage
import com.afapps.notificationSaver.core.shared.toImageBitmap

@Composable
fun DisplayIcon(icon: ByteArray) {
    val platformImage = byteArrayToPlatformImage(icon)
    
    if (platformImage != null) {
        Image(
            bitmap = platformImage.toImageBitmap(), // Converts Bitmap to ImageBitmap
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
    } else {
        // Fallback placeholder if the icon is null
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
    }
}
@Composable
fun DisplayImage(icon: ByteArray) {
    val platformImage = byteArrayToPlatformImage(icon)

    if (platformImage != null) {
        Image(
            bitmap = platformImage.toImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
    } else {
        // Fallback placeholder if the icon is null
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
    }
}
