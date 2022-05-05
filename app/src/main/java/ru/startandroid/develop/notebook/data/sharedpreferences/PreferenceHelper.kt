package ru.startandroid.develop.notebook.data.sharedpreferences

interface PreferenceHelper {

    fun saveString(key: String, value: String)

    fun getString(key: String): String?
}