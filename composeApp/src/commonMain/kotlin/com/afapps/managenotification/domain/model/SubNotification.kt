package com.afapps.managenotification.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

data class SubNotification(
    val id: Long = 0,
    val senderName: String,
    val appName: String,
    val title: String,
    val content: String,
    val time: Long,
    val icon: ByteArray? = null,
    val extras: String? = null,
    val largeIcon: ByteArray? = null,
    val picture: ByteArray? = null,
    val actions: String? = null,
    val smallIconId: Int? = null,
    val subText: String? = null,
    val bigText: String? = null,
    val summaryText: String? = null,
    val isRead: Boolean = false,


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as SubNotification

        if (id != other.id) return false
        if (senderName != other.senderName) return false
        if (appName != other.appName) return false
        if (title != other.title) return false
        if (content != other.content) return false
        if (time != other.time) return false
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

    fun parseExtrasJson(): Map<String, String>? {
        if (extras.isNullOrEmpty()) return null

        val json = Json { ignoreUnknownKeys = true }
        val element = json.parseToJsonElement(extras)

        return if (element is JsonObject) {
            val map = mutableMapOf<String, String>()
            for ((key, value) in element) {
                val valueStr = jsonElementToString(value)
                map[key] = valueStr
            }
             map
        } else {
             null
        }
    }
}


fun jsonElementToString(element: JsonElement): String {
    return when (element) {
        is JsonPrimitive -> {
            if (element.isString) {
                element.content
            } else {
                element.toString()
            }
        }
        is JsonObject -> element.toString()
        else -> element.toString()
    }
}

