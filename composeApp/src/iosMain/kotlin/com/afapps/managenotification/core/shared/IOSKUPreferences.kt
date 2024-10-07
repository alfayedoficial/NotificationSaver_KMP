package com.afapps.managenotification.core.shared

import platform.Foundation.NSUserDefaults

class IOSKUPreferences : Preferences {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override fun setValue(key: String, value: Any?) {
        when (value) {
            is Int -> userDefaults.setInteger(value.toLong(), forKey = key)
            is String -> userDefaults.setObject(value, forKey = key)
            is Double -> userDefaults.setDouble(value, forKey = key)
            is Long -> userDefaults.setInteger(value, forKey = key)
            is Boolean -> userDefaults.setBool(value, forKey = key)
            is Float -> userDefaults.setFloat(value, forKey = key)
            is Any -> userDefaults.setObject(value.toString(), forKey = key) // Basic handling for complex objects
            else -> throw IllegalArgumentException("Unsupported value type")
        }
    }

    override fun getStringValue(key: String, defaultValue: String): String {
        return userDefaults.stringForKey(key) ?: defaultValue
    }

    override fun getIntValue(key: String, defaultValue: Int): Int {
        return userDefaults.integerForKey(key).toInt()
    }

    override fun getLongValue(key: String, defaultValue: Long): Long {
        return userDefaults.integerForKey(key)
    }

    override fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        return userDefaults.boolForKey(key)
    }

    override fun getFloatValue(key: String, defaultValue: Float): Float {
        return userDefaults.floatForKey(key)
    }

    override fun removeKey(key: String) {
        userDefaults.removeObjectForKey(key)
    }

    override fun clear() {
        userDefaults.dictionaryRepresentation().keys.forEach {
            userDefaults.removeObjectForKey(it as String)
        }
    }
}
