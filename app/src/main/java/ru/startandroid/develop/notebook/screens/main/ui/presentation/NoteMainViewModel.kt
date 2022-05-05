package ru.startandroid.develop.notebook.screens.main.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import ru.startandroid.develop.notebook.core.AppThemeModes
import ru.startandroid.develop.notebook.data.db.NoteDao
import ru.startandroid.develop.notebook.data.enitities.NoteEntity
import ru.startandroid.develop.notebook.data.sharedpreferences.PreferenceHelper
import ru.startandroid.develop.notebook.data.sharedpreferences.SharedPreferencesKeys.USER_SELECTED_THEME_MODE_KEY
import ru.startandroid.develop.notebook.screens.main.domain.NoteMainInteractor
import javax.inject.Inject

@HiltViewModel
class NoteMainViewModel @Inject constructor(
    private val interactor: NoteMainInteractor
) : ViewModel() {

    private val _notesState = MutableStateFlow<List<NoteEntity>?>(null)
    val notesState: StateFlow<List<NoteEntity>?> = _notesState.asStateFlow()

    val currentMode: MutableStateFlow<AppThemeModes?> = MutableStateFlow(null)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _notesState.emitAll(interactor.getAllNotes())
        }
    }

    fun deleteAllNotesFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteAllNotes()
        }
    }

    fun deleteSingleNote(note: NoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.delete(note)
        }
    }

    fun saveUserSelectedThemeMode(mode: AppThemeModes) {
        preferences.saveString(USER_SELECTED_THEME_MODE_KEY, mode.name)
    }

    fun getUserSavedThemeMode() {
        val mode = when (preferences.getString(USER_SELECTED_THEME_MODE_KEY)) {
            AppThemeModes.LIGHT_MODE.name -> AppThemeModes.LIGHT_MODE
            AppThemeModes.DARK_MODE.name -> AppThemeModes.DARK_MODE
            else -> AppThemeModes.SYSTEM_ADAPT
        }
        viewModelScope.launch(Dispatchers.IO) {
            currentMode.emit(mode)
        }
    }
}