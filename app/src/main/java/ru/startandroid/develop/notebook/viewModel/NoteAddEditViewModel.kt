package ru.startandroid.develop.notebook.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.startandroid.develop.notebook.model.NoteDao
import ru.startandroid.develop.notebook.model.NoteEntity
import javax.inject.Inject

@HiltViewModel
class NoteAddEditViewModel @Inject constructor(
    private val noteDao: NoteDao
) : ViewModel() {

    fun insertItem(note: NoteEntity) {
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