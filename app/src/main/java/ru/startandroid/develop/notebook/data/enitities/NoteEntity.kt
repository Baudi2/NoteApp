package ru.startandroid.develop.notebook.data.enitities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = NOTE_TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val header: String,
    val description: String,
    val timeStamp: Long = System.currentTimeMillis()
) {
    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(timeStamp)
}

const val NOTE_TABLE_NAME = "note_table"