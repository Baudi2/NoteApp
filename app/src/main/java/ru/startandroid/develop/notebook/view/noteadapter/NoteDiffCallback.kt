package ru.startandroid.develop.notebook.view.noteadapter

import androidx.recyclerview.widget.DiffUtil
import ru.startandroid.develop.notebook.model.NoteEntity

class NoteDiffCallback : DiffUtil.ItemCallback<NoteEntity>() {
    override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity) = oldItem == newItem
}