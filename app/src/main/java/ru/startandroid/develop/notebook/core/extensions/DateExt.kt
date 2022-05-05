package ru.startandroid.develop.notebook.core

import java.util.*


//TODO: add support for 'today' 'yesterday' and else for date
fun Long.formatDateForNote(): Int {
    val cal = Calendar.getInstance(TimeZone.getDefault())
    val today = cal.get(Calendar.DAY_OF_YEAR)
    cal.timeInMillis = this
    val other = cal.get(Calendar.DAY_OF_YEAR)
    return if (other == today) {
        today
    } else -1
}