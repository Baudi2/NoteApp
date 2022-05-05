package ru.startandroid.develop.notebook.core

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String, isLong: Boolean = false) {
    Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}