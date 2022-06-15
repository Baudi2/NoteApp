package ru.startandroid.develop.notebook.screens.main.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.startandroid.develop.notebook.screens.global.model.NoteUi

class NoteDiffCallback : DiffUtil.ItemCallback<NoteUi>() {
    override fun areItemsTheSame(oldItem: NoteUi, newItem: NoteUi) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NoteUi, newItem: NoteUi) = oldItem == newItem
}