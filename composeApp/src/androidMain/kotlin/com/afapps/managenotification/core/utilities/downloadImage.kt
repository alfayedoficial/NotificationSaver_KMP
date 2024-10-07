package com.afapps.managenotification.core.utilities

import android.app.Notification
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Base64
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.ByteArrayOutputStream
import java.io.InputStream

fun downloadImage(url: String): ByteArray? {
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()
    val response = client.newCall(request).execute()
    val inputStream: InputStream? = response.body?.byteStream()

    return inputStream?.let {
        val byteBuffer = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len: Int
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        byteBuffer.toByteArray()
    }
}

 fun getAppName(packageName: String, packageManager: PackageManager): String {
    return try {
        val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
        packageManager.getApplicationLabel(applicationInfo).toString()
    } catch (e: PackageManager.NameNotFoundException) {
        "Unknown"
    }
}

fun getAppIcon(packageName: String, packageManager: PackageManager): ByteArray? {
    return try {
        val drawable = packageManager.getApplicationIcon(packageName)
        val bitmap = if (drawable is BitmapDrawable ){
            drawable.bitmap
        }else if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) && drawable is AdaptiveIconDrawable){
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }else { null}
        bitmap?.let { convertBitmapToByteArray(it) }
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }
}

/**
 * Helper function to convert Bitmap to ByteArray.
 */

fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

fun Notification.getLargeIconBitmap(): Bitmap? {
    // Directly use the deprecated largeIcon field if it exists
    return largeIcon
}

/**
 * Helper function to convert Drawable to Bitmap.
 */
fun Drawable.toBitmap(): Bitmap {
    if (this is BitmapDrawable) {
        return this.bitmap
    }
    val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)
    return bitmap
}

// Helper method to convert bitmap to Base64 (if needed)
fun convertBitmapToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}