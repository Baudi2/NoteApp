package ru.startandroid.develop.notebook.screens.addedit.ui.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.startandroid.develop.notebook.R
import ru.startandroid.develop.notebook.core.Constants.MAIN_DATE_FORMAT
import ru.startandroid.develop.notebook.core.extensions.showKeyboard
import ru.startandroid.develop.notebook.core.extensions.toast
import ru.startandroid.develop.notebook.databinding.NoteAddEditFragmentBinding
import ru.startandroid.develop.notebook.screens.addedit.ui.presentation.NoteAddEditViewModel
import ru.startandroid.develop.notebook.screens.global.model.NoteUi
import ru.startandroid.develop.notebook.ui.baseclasses.BaseFragment
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NoteAddEditFragment :
    BaseFragment<NoteAddEditFragmentBinding>(NoteAddEditFragmentBinding::inflate) {

    private val args: NoteAddEditFragmentArgs by navArgs()

    private val viewModel by viewModels<NoteAddEditViewModel>()

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
                if (note != null) saveItem(note)
                else saveItem(null)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun initViews() {
        binding?.let { binding ->
            with(binding) {
                args.note?.let { note ->
                    noteAddEditEditTextHeader.setText(note.header)
                    noteAddEditDescEditText.setText(note.description)
                }
                if (args.note == null) {
                    noteAddEditDescEditText.requestFocus()
                    requireContext().showKeyboard(noteAddEditDescEditText)
                }
            }
        }
    }

    private fun saveItem(noteForUpdate: NoteUi?) {
        if (validateInputFields()) {
            val date = Date()
            val createdDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
            val formattedTime = SimpleDateFormat(MAIN_DATE_FORMAT, Locale.getDefault()).format(date)
            val note = NoteUi(
                id = noteForUpdate?.id,
                header = binding?.noteAddEditEditTextHeader?.text.toString(),
                description = binding?.noteAddEditDescEditText?.text.toString(),
                createdDate = date,
                creationDay = createdDay,
                formattedTime = formattedTime
            )
            if (noteForUpdate == null) viewModel.insertItem(note)
            else viewModel.updatedItem(note)
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