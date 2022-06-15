package ru.startandroid.develop.notebook.data.enitities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.util.*

@Entity(tableName = NOTE_TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val header: String,
    val description: String,
    val createdDate: Date,
    val creationDay: Int,
    val formattedDay: String
)

const val NOTE_TABLE_NAME = "note_table"