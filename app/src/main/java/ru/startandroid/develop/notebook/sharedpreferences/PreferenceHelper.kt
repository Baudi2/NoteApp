package ru.startandroid.develop.notebook.sharedpreferences

interface PreferenceHelper {

    fun saveString(key: String, value: String)

    fun getString(key: String): String?
}