package ru.startandroid.develop.notebook.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.startandroid.develop.notebook.data.enitities.NoteEntity

@Database(entities = [NoteEntity::class], version = 6,exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao() : NoteDao
}