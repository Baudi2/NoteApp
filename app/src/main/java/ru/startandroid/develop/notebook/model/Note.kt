package ru.startandroid.develop.notebook.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Entity(tableName = "note_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val header: String,
    val description: String,
    val timeStamp: Long = System.currentTimeMillis()
): Parcelable {
    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(timeStamp)
}