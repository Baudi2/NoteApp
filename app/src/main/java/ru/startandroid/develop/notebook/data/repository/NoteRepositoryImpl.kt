package ru.startandroid.develop.notebook.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.startandroid.develop.notebook.data.converters.toDomain
import ru.startandroid.develop.notebook.data.converters.toEntity
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

    override suspend fun insertNote(note: NoteDomainModel) {
        dao.insertNote(note.toEntity())
    }

    override fun getNotes(): Flow<List<NoteDomainModel>> =
        dao.getNotes().flatMapConcat { mapNotes(it) }.flowOn(Dispatchers.IO)

    override suspend fun updateNote(note: NoteDomainModel) {
        dao.update(note.toEntity())
    }

    override suspend fun deleteNote(note: NoteDomainModel) {
        dao.delete(note.toEntity())
    }

    override suspend fun deleteAllNotes() {
        dao.deleteAllNotes()
    }

    override fun saveString(key: String, value: String) {
        preferences.saveString(key, value)
    }

    override fun getString(key: String): String? = preferences.getString(key)

    private fun mapNotes(notes: List<NoteEntity>) = flow {
        emit(notes.map { dbNote ->
            dbNote.toDomain()
        })
    }.flowOn(Dispatchers.IO)
}