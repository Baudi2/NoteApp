package ru.startandroid.develop.notebook.screens.main.ui.adapter

import android.view.View
import ru.startandroid.develop.notebook.screens.global.model.NoteUiModel

interface NoteItemClickListener {
    fun onItemClick(note: NoteUiModel)
    fun onPopupClick(note: NoteUiModel, view: View)
}