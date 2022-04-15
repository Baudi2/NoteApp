package ru.startandroid.develop.notebook.utils

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String, isLong: Boolean = false) {
    Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}