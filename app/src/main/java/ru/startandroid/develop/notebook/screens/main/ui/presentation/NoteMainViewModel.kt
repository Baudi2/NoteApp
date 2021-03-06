package ru.startandroid.develop.notebook.screens.main.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.startandroid.develop.notebook.core.AppThemeModes
import ru.startandroid.develop.notebook.data.sharedpreferences.SharedPreferencesKeys.USER_SELECTED_THEME_MODE_KEY
import ru.startandroid.develop.notebook.domain.model.NoteDomainModel
import ru.startandroid.develop.notebook.screens.global.converter.toDomain
import ru.startandroid.develop.notebook.screens.global.converter.toUi
import ru.startandroid.develop.notebook.screens.global.model.NoteUi
import ru.startandroid.develop.notebook.screens.main.domain.NoteMainInteractor
import javax.inject.Inject

@HiltViewModel
class NoteMainViewModel @Inject constructor(
    private val interactor: NoteMainInteractor
) : ViewModel() {

    private val _notesState = MutableStateFlow<List<NoteUi>>(emptyList())
    val notesState: StateFlow<List<NoteUi>> = _notesState.asStateFlow()

    val currentMode: MutableStateFlow<AppThemeModes?> = MutableStateFlow(null)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val flowUiNotes = interactor.getAllNotes().flatMapConcat { mapAllNotes(it) }
            _notesState.emitAll(flowUiNotes)
        }
    }

    fun insertNote(note: NoteUi) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.insertNote(note.toDomain())
        }
    }

    fun insertAllNotes(notes: List<NoteUi>) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.insertAllNotes(notes.map { it.toDomain() })
        }
    }

    fun deleteAllNotesFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.deleteAllNotes()
        }
    }

    fun deleteSingleNote(note: NoteUi) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.deleteNote(note.toDomain())
        }
    }

    fun saveUserSelectedThemeMode(mode: AppThemeModes) {
        interactor.saveString(USER_SELECTED_THEME_MODE_KEY, mode.name)
    }

    fun getUserSavedThemeMode() {
        val mode = when (interactor.getString(USER_SELECTED_THEME_MODE_KEY)) {
            AppThemeModes.LIGHT_MODE.name -> AppThemeModes.LIGHT_MODE
            AppThemeModes.DARK_MODE.name -> AppThemeModes.DARK_MODE
            else -> AppThemeModes.SYSTEM_ADAPT
        }
        viewModelScope.launch(Dispatchers.IO) {
            currentMode.emit(mode)
        }
    }

    private fun mapAllNotes(domainNotes: List<NoteDomainModel>) = flow {
        emit(domainNotes.map { it.toUi() })
    }.flowOn(Dispatchers.IO)
}