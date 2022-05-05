package ru.startandroid.develop.notebook.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import ru.startandroid.develop.notebook.data.converters.toDomain
import ru.startandroid.develop.notebook.data.db.NoteDao
import ru.startandroid.develop.notebook.data.enitities.NoteEntity
import ru.startandroid.develop.notebook.data.sharedpreferences.PreferenceHelperImpl
import ru.startandroid.develop.notebook.domain.NoteRepository
import ru.startandroid.develop.notebook.domain.model.NoteDomainModel
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao,
    private val preferences: PreferenceHelperImpl
) : NoteRepository {

    override suspend fun insertNote(note: NoteEntity) {
        dao.insertNote(note)
    }

    override fun getNotes(): Flow<List<NoteDomainModel>> =
        dao.getNotes().flatMapConcat { mapNotes(it) }

    override suspend fun updateNote(note: NoteEntity) {
        dao.update(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        dao.delete(note)
    }

    override suspend fun deleteAllNotes() {
        dao.deleteAllNotes()
    }

    override fun saveString(key: String, value: String) {
        preferences.saveString(key, value)
    }

    override fun getString(key: String): String? = preferences.getString(key)

    private fun mapNotes(notes: List<NoteEntity>) = flow<List<NoteDomainModel>> {
        notes.map { dbNote ->
            dbNote.toDomain()
        }
    }
}