package ru.startandroid.develop.notebook.screens.main.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.develop.notebook.screens.global.model.NoteUi

abstract class MainViewHolder(root: View): RecyclerView.ViewHolder(root) {
    open fun bind(note: NoteUi) {}
}