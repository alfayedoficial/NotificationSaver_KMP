package com.afapps.notificationSaver.core.shared

interface Preferences {
    fun setValue(key: String, value: Any?)
    fun getStringValue(key: String, defaultValue: String = ""): String
    fun getIntValue(key: String, defaultValue: Int = 0): Int
    fun getLongValue(key: String, defaultValue: Long = 0L): Long
    fun getBooleanValue(key: String, defaultValue: Boolean = false): Boolean
    fun getFloatValue(key: String, defaultValue: Float = 0f): Float
    fun removeKey(key: String)
    fun clear()
}
