package ru.startandroid.develop.notebook.screens.main.ui.adapter

import android.view.View
import ru.startandroid.develop.notebook.screens.global.model.NoteUi

interface NoteItemClickListener {
    fun onItemClick(note: NoteUi)
    fun onPopupClick(note: NoteUi, view: View)
}