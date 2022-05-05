package ru.startandroid.develop.notebook.screens.addedit.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.startandroid.develop.notebook.data.db.NoteDao
import ru.startandroid.develop.notebook.data.enitities.NoteEntity
import ru.startandroid.develop.notebook.screens.addedit.domain.AddEditInteractor
import ru.startandroid.develop.notebook.screens.global.converter.toDomain
import ru.startandroid.develop.notebook.screens.global.model.NoteUiModel
import javax.inject.Inject

@HiltViewModel
class NoteAddEditViewModel @Inject constructor(
    private val interactor: AddEditInteractor
) : ViewModel() {

    fun insertItem(note: NoteUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.insertNote(note.toDomain())
        }
    }

    fun updatedItem(note: NoteUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.updateNote(note.toDomain())
        }
    }
}