package ru.startandroid.develop.notebook.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.startandroid.develop.notebook.model.NoteEntity
import ru.startandroid.develop.notebook.model.NoteDao
import javax.inject.Inject

@HiltViewModel
class NoteAddEditViewModel @Inject constructor(
    private val noteDao: NoteDao
) : ViewModel() {

    fun insertItem(header: String, desc: String) {
        val note = NoteEntity(header = header, description = desc)
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.insertNote(note)
        }
    }

    fun updatedItem(note: NoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.update(note)
        }
    }
}