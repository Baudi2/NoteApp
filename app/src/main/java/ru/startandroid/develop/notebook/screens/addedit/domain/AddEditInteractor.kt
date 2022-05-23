package ru.startandroid.develop.notebook.screens.addedit.domain

import ru.startandroid.develop.notebook.domain.model.NoteDomainModel

interface AddEditInteractor {

    /**
     * Update existing note
     *
     * @param note Updated note
     */
    suspend fun updateNote(note: NoteDomainModel)

    /**
     * Insert a newly created note into the db
     *
     * If such note already exists the older one will be replaced with new one
     *
     * @param note Newly created note
     */
    suspend fun insertNote(note: NoteDomainModel)
}