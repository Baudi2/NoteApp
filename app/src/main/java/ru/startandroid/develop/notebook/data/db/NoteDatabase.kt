package ru.startandroid.develop.notebook.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.startandroid.develop.notebook.data.enitities.NoteEntity

@TypeConverters(TimeStampConverter::class)
@Database(entities = [NoteEntity::class], version = 1,exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao() : NoteDao
}