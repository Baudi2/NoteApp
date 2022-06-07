package ru.startandroid.develop.notebook.screens.main.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.startandroid.develop.notebook.R
import ru.startandroid.develop.notebook.databinding.NoteItemBinding
import ru.startandroid.develop.notebook.databinding.NoteSearchItemBinding
import ru.startandroid.develop.notebook.screens.global.model.NoteUi

class NoteAdapter(
    private val listener: NoteItemClickListener,
    private val searchListener: SearchItemListener
) : ListAdapter<NoteUi, MainViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        when (viewType) {
            R.layout.note_search_item -> {
                val binding =
                    NoteSearchItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return HeaderViewHolder(binding, searchListener)
            }
            R.layout.note_item -> {
                val binding =
                    NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return NoteViewHolder(binding, listener)
            }
            else -> throw RuntimeException("Unknown type $viewType, you should modify createViewHolder")
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> R.layout.note_search_item
        else -> R.layout.note_item
    }
}