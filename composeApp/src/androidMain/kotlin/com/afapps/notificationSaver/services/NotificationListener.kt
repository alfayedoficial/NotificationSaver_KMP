package com.afapps.notificationSaver.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.os.UserHandle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.afapps.notificationSaver.core.utilities.toBitmap
import com.afapps.notificationSaver.data.entities.SubNotificationEntity
import com.afapps.notificationSaver.domain.repo.NotificationRepo
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream

class NotificationListener : NotificationListenerService() {

    private val notificationRepo by inject<NotificationRepo>()

    override fun onNotificationPosted(sbn: StatusBarNotification?) {

        val context = applicationContext
        val notificationData = sbn?.let { convertNotificationToData(it, context) }
        val subNotificationEntity = notificationData?.let { notificationDataToEntity(it) }

        if (subNotificationEntity != null) {
            CoroutineScope(Dispatchers.IO).launch {
                notificationRepo.addNotification(subNotificationEntity)
            }
        }

    }
  

    fun convertNotificationToData(sbn: StatusBarNotification, context: Context): NotificationData {
        val notification = sbn.notification
        val packageName = sbn.packageName

        val pm = context.packageManager

        // Retrieve the application name
        val appName: String? = try {
            val appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            pm.getApplicationLabel(appInfo).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }

        // Retrieve the application icon and convert it to Bitmap
        val appIcon: Bitmap? = try {
            val appIconDrawable = pm.getApplicationIcon(packageName)
            appIconDrawable?.toBitmap()
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }

        val extras = notification.extras

        // Extract standard fields from the notification
        val title = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString()
        val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()
        val subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)?.toString()
        val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString()
        val summaryText = extras.getCharSequence(Notification.EXTRA_SUMMARY_TEXT)?.toString()
        val whenTime = notification.`when`
        val smallIconId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notification.smallIcon?.resId
        } else {
            @Suppress("DEPRECATION")
            notification.icon
        }

        // Extract images
        val bigPicture = getBitmapFromExtras(extras, Notification.EXTRA_PICTURE, context)
        val largeIcon = getLargeIcon(notification, context)

        // Extract actions
        val actions = getNotificationActions(notification, context)

        // Convert remaining extras to a Map
        val extrasMap = bundleToMap(extras)

        return NotificationData(
            packageName = packageName,
            appName = appName,
            appIcon = appIcon,
            title = title,
            text = text,
            subText = subText,
            bigText = bigText,
            summaryText = summaryText,
            bigPicture = bigPicture,
            largeIcon = largeIcon,
            smallIconId = smallIconId,
            whenTime = whenTime,
            actions = actions,
            extras = extrasMap
        )
    }

    data class NotificationAction(
        val icon: Bitmap?,             // Icon of the action
        val title: String?,            // Title of the action
        val actionIntent: PendingIntent? // PendingIntent to be triggered when the action is invoked
    )

    data class NotificationData(
        val packageName: String,           // Package name of the app posting the notification
        val appName: String?,              // Application name
        val appIcon: Bitmap?,              // Application icon
        val title: String?,
        val text: String?,
        val subText: String?,
        val bigText: String?,
        val summaryText: String?,
        val bigPicture: Bitmap?,
        val largeIcon: Bitmap?,
        val smallIconId: Int?,
        val whenTime: Long,
        val extras: Map<String, Any?>,
        val actions: List<NotificationAction>?, // Add this line

    )

    fun notificationDataToEntity(notificationData: NotificationData): SubNotificationEntity {
        return SubNotificationEntity(
            senderName = notificationData.packageName,                 // Now stores packageName
            appName = notificationData.appName ?: "",
            icon = bitmapToByteArray(notificationData.appIcon),
            title = notificationData.title ?: "",
            content = notificationData.text ?: "",
            subText = notificationData.subText,
            bigText = notificationData.bigText,
            summaryText = notificationData.summaryText,
            time = notificationData.whenTime,
            smallIconId = notificationData.smallIconId,
            largeIcon = bitmapToByteArray(notificationData.largeIcon),
            picture = bitmapToByteArray(notificationData.bigPicture),
            actions = actionsToJson(notificationData.actions),
            extras = extrasToJson(notificationData.extras)
        )
    }

    fun getNotificationActions(
        notification: Notification,
        context: Context
    ): List<NotificationAction>? {
        val actionsList = mutableListOf<NotificationAction>()

        if (notification.actions != null) {
            for (action in notification.actions) {
                val iconBitmap = getActionIconBitmap(action, context)
                val title = action.title?.toString()
                val actionIntent = action.actionIntent

                val notificationAction = NotificationAction(
                    icon = iconBitmap,
                    title = title,
                    actionIntent = actionIntent
                )
                actionsList.add(notificationAction)
            }
            return actionsList
        }
        return null
    }

    fun getLargeIcon(notification: Notification, context: Context): Bitmap? {
        // First, try to get the large icon from the extras
        val extras = notification.extras
        val largeIconFromExtras =
            getBitmapFromExtras(extras, Notification.EXTRA_LARGE_ICON, context)
        if (largeIconFromExtras != null) {
            return largeIconFromExtras
        }

        // If not found in extras, try to get it from the notification
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val icon = notification.getLargeIcon()
            if (icon != null) {
                icon.loadDrawable(context)?.toBitmap()
            } else {
                null
            }
        } else {
            @Suppress("DEPRECATION")
            notification.largeIcon
        }
    }

    fun getActionIconBitmap(action: Notification.Action, context: Context): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            action.getIcon()?.loadDrawable(context)?.toBitmap()
        } else {
            @Suppress("DEPRECATION")
            action.icon?.let { iconResId ->
                context.resources.getDrawable(iconResId)?.toBitmap()
            }
        }
    }

    fun bundleToMap(bundle: Bundle): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        val keys = bundle.keySet()
        for (key in keys) {
            map[key] = bundle.get(key)
        }
        return map
    }

    fun bitmapToByteArray(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) return null
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    fun actionsToJson(actions: List<NotificationAction>?): String? {
        if (actions == null) return null
        val gson = Gson()
        return gson.toJson(actions)
    }

    fun extrasToJson(extras: Map<String, Any?>?): String? {
        if (extras == null) return null
        val gson = Gson()
        return gson.toJson(extras)
    }

    fun getBitmapFromExtras(extras: Bundle, key: String, context: Context): Bitmap? {
        val obj: Any? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            extras.getParcelable(key, Parcelable::class.java)
        } else {
            @Suppress("DEPRECATION")
            extras.get(key)
        }

        return when (obj) {
            is Bitmap -> obj
            is Icon -> iconToBitmap(obj, context)
            else -> null
        }
    }

    fun iconToBitmap(icon: Icon, context: Context): Bitmap? {
        val drawable = icon.loadDrawable(context) ?: return null
        return drawable.toBitmap()
    }


}
