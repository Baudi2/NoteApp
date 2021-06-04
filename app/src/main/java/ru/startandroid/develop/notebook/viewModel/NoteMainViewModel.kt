package ru.startandroid.develop.notebook.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.startandroid.develop.notebook.model.Note
import ru.startandroid.develop.notebook.model.NoteDao
import ru.startandroid.develop.notebook.utils.shortToast
import javax.inject.Inject

@HiltViewModel
class NoteMainViewModel @Inject constructor(
    private val noteDao: NoteDao
) : ViewModel() {

    val notes = noteDao.getNotes().asLiveData()

    fun deleteAllNotesFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteAllNotes()
            withContext(Dispatchers.Main) {
                shortToast("Все заметки удалены")
            }
        }
    }

    fun deleteSingleNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.delete(note)
        }
    }
}