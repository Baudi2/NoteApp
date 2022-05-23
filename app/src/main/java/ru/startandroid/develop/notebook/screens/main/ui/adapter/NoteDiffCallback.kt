package ru.startandroid.develop.notebook.screens.main.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.startandroid.develop.notebook.screens.global.model.NoteUiModel

class NoteDiffCallback : DiffUtil.ItemCallback<NoteUiModel>() {
    override fun areItemsTheSame(oldItem: NoteUiModel, newItem: NoteUiModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NoteUiModel, newItem: NoteUiModel) = oldItem == newItem
}