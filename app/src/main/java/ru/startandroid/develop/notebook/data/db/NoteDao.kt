package ru.startandroid.develop.notebook.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.startandroid.develop.notebook.data.enitities.NOTE_TABLE_NAME
import ru.startandroid.develop.notebook.data.enitities.NoteEntity

@Dao
interface NoteDao {

    @Query("SELECT * FROM $NOTE_TABLE_NAME ORDER BY createdDate DESC")
    fun getNotes() : Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNotes(notes: List<NoteEntity>)

    @Update
    suspend fun update(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)

    @Query("DELETE FROM $NOTE_TABLE_NAME")
    suspend fun deleteAllNotes()
}