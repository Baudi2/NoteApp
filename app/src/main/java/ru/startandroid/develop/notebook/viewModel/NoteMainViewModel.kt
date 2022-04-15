package ru.startandroid.develop.notebook.viewModel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.startandroid.develop.notebook.model.NoteEntity
import ru.startandroid.develop.notebook.model.NoteDao
import ru.startandroid.develop.notebook.sharedpreferences.PreferenceHelper
import ru.startandroid.develop.notebook.sharedpreferences.SharedPreferencesKeys.USER_SELECTED_THEME_MODE_KEY
import ru.startandroid.develop.notebook.utils.AppThemeModes
import javax.inject.Inject

@HiltViewModel
class NoteMainViewModel @Inject constructor(
    private val noteDao: NoteDao,
    private val preferences: PreferenceHelper
) : ViewModel() {

    private val _notesState = MutableStateFlow<List<NoteEntity>?>(null)
    val notesState: StateFlow<List<NoteEntity>?> = _notesState.asStateFlow()

    val currentMode: MutableStateFlow<AppThemeModes?> = MutableStateFlow(null)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _notesState.emitAll(noteDao.getNotes())
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
        val mode =  when (preferences.getString(USER_SELECTED_THEME_MODE_KEY)) {
            AppThemeModes.LIGHT_MODE.name -> AppThemeModes.LIGHT_MODE
            AppThemeModes.DARK_MODE.name -> AppThemeModes.DARK_MODE
            else -> AppThemeModes.SYSTEM_ADAPT
        }
        viewModelScope.launch(Dispatchers.IO) {
            currentMode.emit(mode)
        }
    }
}