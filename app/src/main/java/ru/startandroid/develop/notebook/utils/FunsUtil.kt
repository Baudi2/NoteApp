package ru.startandroid.develop.notebook.utils

import android.widget.Toast

fun shortToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}


