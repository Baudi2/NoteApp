package ru.startandroid.develop.notebook.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelperImpl(context: Context) : PreferenceHelper {

    private val name = "SharedPreferencesHelper"
    private val preferences: SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    override fun saveString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String): String? {
        return preferences.getString(key, "")
    }
}