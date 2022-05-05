package ru.startandroid.develop.notebook.screens.main.domain

import kotlinx.coroutines.flow.Flow
import ru.startandroid.develop.notebook.data.enitities.NoteEntity
import ru.startandroid.develop.notebook.domain.model.NoteDomainModel

interface NoteMainInteractor {

    /**
     * Get all saved notes from db
     *
     * @return [Flow] of [List] of [NoteEntity]
     */
    fun getAllNotes(): Flow<List<NoteDomainModel>>

    /**
     * Delete a note from db
     *
     * @param note note that we want to delete
     */
    suspend fun deleteNote(note: NoteDomainModel)

    /**
     * Delete all note from db
     */
    suspend fun deleteAllNotes()

    /**
     * Allows to save a string in sharedPreferences
     *
     * @param key the with which you can access saved string
     * @param value value of the saved string
     */
    fun saveString(key: String, value: String)

    /**
     * Allows to get the saved string from sharedPreferences
     *
     * @param key the with which you can access saved string
     *
     * @return [String] will return null if no string was saved with the given key
     */
    fun getString(key: String): String?
}