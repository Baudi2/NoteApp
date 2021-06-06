package ru.startandroid.develop.notebook.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.startandroid.develop.notebook.R
import ru.startandroid.develop.notebook.databinding.NoteAddEditFragmentBinding
import ru.startandroid.develop.notebook.model.Note
import ru.startandroid.develop.notebook.viewModel.NoteAddEditViewModel

@AndroidEntryPoint
class NoteAddEditFragment : Fragment(R.layout.note_add_edit_fragment) {
    private lateinit var binding: NoteAddEditFragmentBinding
    private val args: NoteAddEditFragmentArgs by navArgs()
    private val viewModel by viewModels<NoteAddEditViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NoteAddEditFragmentBinding.bind(view)

        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        if (args.note != null) {
            val note = args.note
            with(binding) {
                editTextHeader.setText(note!!.header)
                descEditText.setText(note.description)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.noteMainFragment -> {
                if (args.note == null) {
                    saveItem()
                    val action =
                        NoteAddEditFragmentDirections.actionNoteAddEditFragmentToNoteMainFragment()
                    findNavController().navigate(action)
                } else {
                    updateNote()
                    val action =
                        NoteAddEditFragmentDirections.actionNoteAddEditFragmentToNoteMainFragment()
                    findNavController().navigate(action)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveItem() {
        val header = binding.editTextHeader.text.toString()
        val desc = binding.descEditText.text.toString()
        viewModel.insertItem(header, desc)
    }

    private fun updateNote() {
        val note = args.note
        val id = note!!.id
        val header = binding.editTextHeader.text.toString()
        val description = binding.descEditText.text.toString()
        val editedNote = Note(id = id, header = header, description = description)
        viewModel.updatedItem(editedNote)
    }
}