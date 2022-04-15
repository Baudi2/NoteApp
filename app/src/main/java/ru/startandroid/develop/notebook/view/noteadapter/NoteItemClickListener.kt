package ru.startandroid.develop.notebook.view.noteadapter

import android.view.View
import ru.startandroid.develop.notebook.model.NoteEntity

interface NoteItemClickListener {
    fun onItemClick(note: NoteEntity)
    fun onPopupClick(note: NoteEntity, view: View)
}