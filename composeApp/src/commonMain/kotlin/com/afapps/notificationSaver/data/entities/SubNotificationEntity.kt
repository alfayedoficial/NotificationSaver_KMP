package com.afapps.notificationSaver.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.afapps.notificationSaver.domain.model.SubNotification

@Entity(
    tableName = "sub_notifications",
    foreignKeys = [
        ForeignKey(
            entity = NotificationEntity::class,
            parentColumns = ["senderName"],
            childColumns = ["senderName"],
            onDelete = CASCADE
        )
    ],
    indices = [Index(value = ["senderName"])] // Create an index on senderName
)
data class SubNotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val senderName: String,
    val appName: String,
    val title: String,
    val content: String,
    val time: Long,
    val image: ByteArray? = null,
    val icon: ByteArray? = null,
    val extras: String? = null,
    val largeIcon: ByteArray? = null, // New field for large icon
    val picture: ByteArray? = null, // New field for picture
    val actions: String? = null, // New field for actions (JSON string)
    val smallIconId: Int? = null, // New field for small icon ID
    val subText: String? = null,// New field for sub text
    val bigText: String? = null, // New field for big text
    val summaryText: String? = null, // New field for summary text
    val isRead: Boolean = false,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as SubNotificationEntity

        if (id != other.id) return false
        if (senderName != other.senderName) return false
        if (appName != other.appName) return false
        if (title != other.title) return false
        if (content != other.content) return false
        if (time != other.time) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (icon != null) {
            if (other.icon == null) return false
            if (!icon.contentEquals(other.icon)) return false
        } else if (other.icon != null) return false
        if (extras != other.extras) return false
        if (largeIcon != null) {
            if (other.largeIcon == null) return false
            if (!largeIcon.contentEquals(other.largeIcon)) return false
        } else if (other.largeIcon != null) return false
        if (picture != null) {
            if (other.picture == null) return false
            if (!picture.contentEquals(other.picture)) return false
        } else if (other.picture != null) return false
        if (actions != other.actions) return false
        if (smallIconId != other.smallIconId) return false
        if (subText != other.subText) return false
        if (bigText != other.bigText) return false
        if (summaryText != other.summaryText) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + senderName.hashCode()
        result = 31 * result + appName.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + (icon?.contentHashCode() ?: 0)
        result = 31 * result + (extras?.hashCode() ?: 0)
        result = 31 * result + (largeIcon?.contentHashCode() ?: 0)
        result = 31 * result + (picture?.contentHashCode() ?: 0)
        result = 31 * result + (actions?.hashCode() ?: 0)
        result = 31 * result + (smallIconId ?: 0)
        result = 31 * result + (subText?.hashCode() ?: 0)
        result = 31 * result + (bigText?.hashCode() ?: 0)
        result = 31 * result + (summaryText?.hashCode() ?: 0)
        return result
    }

}