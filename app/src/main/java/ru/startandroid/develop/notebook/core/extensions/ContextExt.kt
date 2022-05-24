package ru.startandroid.develop.notebook.core.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun Context.toast(message: String, isLong: Boolean = false) {
    Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

fun Context.showKeyboard(view: View) {
    val imn = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imn.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT)
}