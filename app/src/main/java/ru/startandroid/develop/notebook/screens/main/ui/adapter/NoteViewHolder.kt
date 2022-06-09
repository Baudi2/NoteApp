package ru.startandroid.develop.notebook.screens.main.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.develop.notebook.databinding.NoteItemBinding
import ru.startandroid.develop.notebook.screens.global.model.NoteUi

class NoteViewHolder(
    private val binding: NoteItemBinding,
    private val listener: NoteItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(note: NoteUi) {
        with(binding) {
            noteItemHeaderTextView.text = note.header
            noteItemDescTextView.text = note.description
            noteItemTimeStamp.text = note.createdDateFormatted

            if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                root.setOnClickListener { listener.onItemClick(note) }
                noteItemSelectedItemMenu.setOnClickListener { listener.onPopupClick(note, it) }
            }
        }
    }
}