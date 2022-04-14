package ru.startandroid.develop.notebook.viewModel

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.startandroid.develop.notebook.model.Note
import ru.startandroid.develop.notebook.model.NoteDao
import ru.startandroid.develop.notebook.sharedpreferences.PreferenceHelper
import ru.startandroid.develop.notebook.sharedpreferences.SharedPreferencesKeys.USER_SELECTED_THEME_MODE_KEY
import ru.startandroid.develop.notebook.utils.AppThemeModes
import ru.startandroid.develop.notebook.utils.toast
import javax.inject.Inject

@HiltViewModel
class NoteMainViewModel @Inject constructor(
    private val noteDao: NoteDao,
    private val preferences: PreferenceHelper
) : ViewModel() {

    val notes = noteDao.getNotes().asLiveData()

    private val _showText = MutableLiveData<Boolean>()
    val showText: LiveData<Boolean>
        get() = _showText

    val currentMode = MutableLiveData<AppThemeModes>()

    fun deleteAllNotesFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteAllNotes()
            withContext(Dispatchers.Main) {
                toast("Все заметки удалены")
            }
        }
    }

    fun deleteSingleNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.delete(note)
        }
    }

    fun delayedShowNoActiveNotes() {
        _showText.value = false
        viewModelScope.launch(Dispatchers.IO) {
            delay(400)
            _showText.postValue(true)
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
        currentMode.postValue(mode)
    }
}