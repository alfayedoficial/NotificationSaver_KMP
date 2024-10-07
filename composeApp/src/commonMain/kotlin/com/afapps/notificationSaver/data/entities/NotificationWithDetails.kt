package com.afapps.notificationSaver.data.entities

data class NotificationWithDetails(
    val id: Long,
    val senderName: String,
    val appName: String,
    val icon: ByteArray? = null,
    val lastTime: Long? = null,
    val count: Int = 0,
    val isRead: Boolean = false,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as NotificationWithDetails

        if (id != other.id) return false
        if (senderName != other.senderName) return false
        if (appName != other.appName) return false
        if (icon != null) {
            if (other.icon == null) return false
            if (!icon.contentEquals(other.icon)) return false
        } else if (other.icon != null) return false
        if (lastTime != other.lastTime) return false
        if (count != other.count) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + senderName.hashCode()
        result = 31 * result + appName.hashCode()
        result = 31 * result + (icon?.contentHashCode() ?: 0)
        result = 31 * result + (lastTime?.hashCode() ?: 0)
        result = 31 * result + count
        return result
    }
}
