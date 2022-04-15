package ru.startandroid.develop.notebook.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.startandroid.develop.notebook.R
import ru.startandroid.develop.notebook.databinding.NoteAddEditFragmentBinding
import ru.startandroid.develop.notebook.model.NoteEntity
import ru.startandroid.develop.notebook.utils.toast
import ru.startandroid.develop.notebook.viewModel.NoteAddEditViewModel

@AndroidEntryPoint
class NoteAddEditFragment : Fragment() {

    private var binding: NoteAddEditFragmentBinding? = null

    private val args: NoteAddEditFragmentArgs by navArgs()

    private val viewModel by viewModels<NoteAddEditViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = NoteAddEditFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.noteMainFragment -> {
                val note = args.note
                if (note != null) updateNote(note.id)
                else saveItem()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initViews() {
        binding?.let { binding ->
            with(binding) {
                args.note?.let { note ->
                    noteAddEditEditTextHeader.setText(note.header)
                    noteAddEditDescEditText.setText(note.description)
                }
            }
        }
    }

    private fun saveItem() {
        if (validateInputFields()) {
            viewModel.insertItem(
                NoteEntity(
                    header = binding?.noteAddEditEditTextHeader?.text.toString(),
                    description = binding?.noteAddEditDescEditText?.text.toString()
                )
            )
            val action =
                NoteAddEditFragmentDirections.actionNoteAddEditFragmentToNoteMainFragment()
            findNavController().navigate(action)
        }
    }

    private fun updateNote(noteId: Int) {
        if (validateInputFields()) {
            viewModel.updatedItem(
                NoteEntity(
                    id = noteId,
                    header = binding?.noteAddEditEditTextHeader?.text.toString(),
                    description = binding?.noteAddEditDescEditText?.text.toString()
                )
            )
            val action =
                NoteAddEditFragmentDirections.actionNoteAddEditFragmentToNoteMainFragment()
            findNavController().navigate(action)
        }
    }

    private fun validateInputFields(): Boolean {
        binding?.let { binding ->
            with(binding) {
                if (noteAddEditEditTextHeader.text.trim()
                        .isEmpty() && noteAddEditDescEditText.text.trim().isEmpty()
                ) {
                    requireContext().toast(
                        getString(R.string.empty_header_and_description_error_message),
                        true
                    )
                    return false
                }
                if (noteAddEditEditTextHeader.text.trim().isEmpty()) {
                    requireContext().toast(getString(R.string.empty_header_error_message), true)
                    return false
                }
                if (noteAddEditDescEditText.text.trim().isEmpty()) {
                    requireContext().toast(getString(R.string.empty_desc_error_message), true)
                    return false
                }
            }
        }
        return true
    }
}