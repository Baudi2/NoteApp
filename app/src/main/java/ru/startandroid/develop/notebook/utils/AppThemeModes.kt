package ru.startandroid.develop.notebook.utils

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@kotlinx.parcelize.Parcelize
enum class AppThemeModes : Parcelable {
    DARK_MODE,
    LIGHT_MODE,
    SYSTEM_ADAPT
}