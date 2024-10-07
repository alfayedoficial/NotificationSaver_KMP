package com.afapps.notificationSaver.core.shared

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class AndroidKUPreferences(context: Context) : Preferences {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun setValue(key: String, value: Any?) {
        when (value) {
            is Int -> editor.putInt(key, value).apply()
            is String -> editor.putString(key, value).apply()
            is Double -> editor.putString(key, value.toString()).apply()
            is Long -> editor.putLong(key, value).apply()
            is Boolean -> editor.putBoolean(key, value).apply()
            is Float -> editor.putFloat(key, value).apply()
            is Any -> editor.putString(key, Gson().toJson(value)).apply()
            else -> throw IllegalArgumentException("Unsupported value type")
        }
    }

    override fun getStringValue(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    override fun getIntValue(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    override fun getLongValue(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    override fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    override fun getFloatValue(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    override fun removeKey(key: String) {
        editor.remove(key).apply()
    }

    override fun clear() {
        editor.clear().apply()
    }
}
