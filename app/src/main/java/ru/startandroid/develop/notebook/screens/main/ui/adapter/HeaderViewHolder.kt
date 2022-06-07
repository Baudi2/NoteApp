package ru.startandroid.develop.notebook.screens.main.ui.adapter

import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.startandroid.develop.notebook.core.extensions.showKeyboard
import ru.startandroid.develop.notebook.databinding.NoteSearchItemBinding
import ru.startandroid.develop.notebook.screens.global.model.NoteSearchHeaderModel
import ru.startandroid.develop.notebook.screens.global.model.NoteUi

class HeaderViewHolder(private val binding: NoteSearchItemBinding, private val listener: SearchItemListener) : MainViewHolder(binding.root) {
    override fun bind(note: NoteUi) {
        val layoutParams = itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
        layoutParams.isFullSpan = true
        if (note is NoteSearchHeaderModel) {
            with(binding) {
                noteSearchItemEditText.setText(note.query)
                noteSearchItemClearButton.isVisible = noteSearchItemEditText.text.isNotEmpty()
                noteSearchItemEditTextLayout.setOnClickListener {
                    noteSearchItemEditText.requestFocus()
                    root.context.showKeyboard(noteSearchItemEditText)
                }
                noteSearchItemClearButton.setOnClickListener { noteSearchItemEditText.setText("") }
                noteSearchItemEditText.addTextChangedListener {
                    noteSearchItemClearButton.isVisible = !it.isNullOrEmpty()
                    listener.onSearchQueryChanged(it.toString())
                }
            }
        }
    }
}