package ru.startandroid.develop.notebook.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.develop.notebook.databinding.NoteItemBinding
import ru.startandroid.develop.notebook.model.NoteEntity

class NoteAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<NoteEntity, NoteAdapter.NoteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class NoteViewHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val note = getItem(position)
                        listener.onItemClick(note)
                    }
                }
                noteItemSelectedItemMenu.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val note = getItem(position)
                        listener.onPopupClick(note, it)
                    }
                }
            }
        }

        fun bind(note: NoteEntity) {
            with(binding) {
                noteItemHeaderTextView.text = note.header
                noteItemDescTextView.text = note.description
                noteItemTimeStamp.text = note.createdDateFormatted
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity) =
            oldItem == newItem
    }

    interface OnItemClickListener {
        fun onItemClick(note: NoteEntity)
        fun onPopupClick(note: NoteEntity, view: View)
    }
}