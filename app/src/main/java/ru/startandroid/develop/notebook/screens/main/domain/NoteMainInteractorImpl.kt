package ru.startandroid.develop.notebook.screens.main.domain

import ru.startandroid.develop.notebook.domain.NoteRepository
import ru.startandroid.develop.notebook.domain.model.NoteDomainModel

class NoteMainInteractorImpl(private val repository: NoteRepository) : NoteMainInteractor {

    override fun getAllNotes() = repository.getNotes()

    override suspend fun deleteNote(note: NoteDomainModel) {
        repository.deleteNote(note)
    }

    override suspend fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    override fun saveString(key: String, value: String) {
        repository.saveString(key, value)
    }

    override fun getString(key: String): String = repository.getString(key).orEmpty()
}