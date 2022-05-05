package ru.startandroid.develop.notebook.screens.main.ui.adapter

import android.view.View
import ru.startandroid.develop.notebook.data.enitities.NoteEntity

interface NoteItemClickListener {
    fun onItemClick(note: NoteEntity)
    fun onPopupClick(note: NoteEntity, view: View)
}