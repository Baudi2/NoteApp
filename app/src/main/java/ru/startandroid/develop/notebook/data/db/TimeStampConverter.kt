package ru.startandroid.develop.notebook.data.db

import androidx.room.TypeConverter
import java.util.*

class TimeStampConverter {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? = if (value == null) null else Date(value)

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? = date?.time
}