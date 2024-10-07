package com.afapps.managenotification.data.entities

import androidx.room.Entity
import androidx.room.Index

import androidx.room.PrimaryKey

@Entity(
    tableName = "notifications",
    indices = [Index(value = ["senderName"], unique = true)] // Add unique index on senderName
)data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val senderName: String,
    val appName: String,
    val icon: ByteArray? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as NotificationEntity

        if (id != other.id) return false
        if (senderName != other.senderName) return false
        if (appName != other.appName) return false
        if (icon != null) {
            if (other.icon == null) return false
            if (!icon.contentEquals(other.icon)) return false
        } else if (other.icon != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + senderName.hashCode()
        result = 31 * result + appName.hashCode()
        result = 31 * result + (icon?.contentHashCode() ?: 0)
        return result
    }
}


