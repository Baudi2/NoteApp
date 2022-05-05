package ru.startandroid.develop.notebook.core

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
enum class AppThemeModes : Parcelable {
    DARK_MODE,
    LIGHT_MODE,
    SYSTEM_ADAPT
}