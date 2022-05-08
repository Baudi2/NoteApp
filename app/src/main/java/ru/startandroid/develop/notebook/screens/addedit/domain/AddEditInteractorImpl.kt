package ru.startandroid.develop.notebook.screens.addedit.domain

import ru.startandroid.develop.notebook.domain.NoteRepository
import ru.startandroid.develop.notebook.domain.model.NoteDomainModel
import javax.inject.Inject

class AddEditInteractorImpl(private val repository: NoteRepository): AddEditInteractor {

    override suspend fun updateNote(note: NoteDomainModel) {
        repository.updateNote(note)
    }

    override suspend fun insertNote(note: NoteDomainModel) {
        repository.insertNote(note)
    }
}