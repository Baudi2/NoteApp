package ru.startandroid.develop.notebook.screens.main.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.startandroid.develop.notebook.domain.NoteDateFormatter
import ru.startandroid.develop.notebook.domain.NoteRepository
import ru.startandroid.develop.notebook.domain.model.NoteDomainModel

class NoteMainInteractorImpl(
    private val repository: NoteRepository,
    private val dateFormatter: NoteDateFormatter
) : NoteMainInteractor {

    override suspend fun insertNote(note: NoteDomainModel) {
        repository.insertNote(note)
    }

    override suspend fun insertAllNotes(notes: List<NoteDomainModel>) {
        repository.insertAllNotes(notes)
    }

    override fun getAllNotes() = repository.getNotes().flatMapConcat { formatNoteDate(it) }

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

    private fun formatNoteDate(domainNotes: List<NoteDomainModel>) = flow {
        val formattedNotes = domainNotes.map { dateFormatter.formatDate(it) }
        emit(formattedNotes)
    }.flowOn(Dispatchers.IO)
}