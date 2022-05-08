package ru.startandroid.develop.notebook.screens.main.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.startandroid.develop.notebook.databinding.NoteItemBinding
import ru.startandroid.develop.notebook.screens.global.model.NoteUiModel

class NoteAdapter(
    private val listener: NoteItemClickListener
) : ListAdapter<NoteUiModel, NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}