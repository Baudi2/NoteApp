package ru.startandroid.develop.notebook.viewModel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    private val _showText = MutableLiveData<Boolean>()
    val showText: LiveData<Boolean>
        get() = _showText

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

    fun delayedShowNoActiveNotes() {
        _showText.value = false
        viewModelScope.launch(Dispatchers.IO) {
            delay(400)
//            withContext(Dispatchers.Main) {
//                _showText.value = true
//            }
            _showText.postValue(true)
        }
    }
}