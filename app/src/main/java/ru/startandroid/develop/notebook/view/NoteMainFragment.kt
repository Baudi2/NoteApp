package ru.startandroid.develop.notebook.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.startandroid.develop.notebook.R
import ru.startandroid.develop.notebook.databinding.NoteMainFragmentBinding
import ru.startandroid.develop.notebook.model.Note
import ru.startandroid.develop.notebook.utils.APP_ACTIVITY
import ru.startandroid.develop.notebook.utils.AppThemeModes
import ru.startandroid.develop.notebook.utils.toast
import ru.startandroid.develop.notebook.view.DarkModeChooserDialog.Companion.DARK_MODE_CHOOSER_DIALOG_MODE_KEY
import ru.startandroid.develop.notebook.view.DarkModeChooserDialog.Companion.DARK_MODE_CHOOSER_DIALOG_RESULT_KEY
import ru.startandroid.develop.notebook.viewModel.NoteMainViewModel

@AndroidEntryPoint
class NoteMainFragment : Fragment(R.layout.note_main_fragment), NoteAdapter.OnItemClickListener {
    private lateinit var binding: NoteMainFragmentBinding
    private val viewModel by viewModels<NoteMainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NoteMainFragmentBinding.bind(view)

        setHasOptionsMenu(true)


        val adapter = NoteAdapter(this)
        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        with(binding) {
            fabAddNote.setOnClickListener {
                navigateToAdd()
            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
        }

        viewModel.notes.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.noActiveNotes.visibility = View.GONE
            }
            adapter.submitList(it)
        }

        modeChooserResultListener()
    }

    private fun navigateToAdd() {
        val action =
            NoteMainFragmentDirections.actionNoteMainFragmentToNoteAddEditFragment("Создать заметку")
        findNavController().navigate(action)
    }

    override fun onItemClick(note: Note) {
        val action =
            NoteMainFragmentDirections.actionNoteMainFragmentToNoteAddEditFragment("Изменить", note)
        findNavController().navigate(action)
    }

    override fun onPopupClick(note: Note, view: View) {
        showPopup(view, R.menu.main_menu_popup, "Заметка удалена", R.id.delete_one_item, note)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.main_menu_delete -> {
                deleteAllNotes()
                true
            }
            R.id.main_menu_dark_mode -> {
                showDarkModeDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun deleteAllNotes() {
        viewModel.deleteAllNotesFromDb()
        viewModel.showText.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.noActiveNotes.visibility = View.VISIBLE
            }
        }
        viewModel.delayedShowNoActiveNotes()
    }

    private fun showDarkModeDialog() {
        val action = NoteMainFragmentDirections.actionNoteMainFragmentToDarkModeChooserDialog()
        findNavController().navigate(action)
    }

    private fun modeChooserResultListener() {
        setFragmentResultListener(DARK_MODE_CHOOSER_DIALOG_RESULT_KEY) { key, bundle ->
            when (key) {
                DARK_MODE_CHOOSER_DIALOG_RESULT_KEY -> {
                    changeAppMode(
                        bundle.getParcelable(DARK_MODE_CHOOSER_DIALOG_MODE_KEY)
                            ?: return@setFragmentResultListener
                    )
                }
            }
        }
    }

    private fun changeAppMode(mode: AppThemeModes) {
        when (mode) {
            AppThemeModes.DARK_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            AppThemeModes.LIGHT_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            AppThemeModes.SYSTEM_ADAPT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun showPopup(view: View, menu: Int, message: String, menuItemId: Int, note: Note) {
        val menuBuilder = MenuBuilder(APP_ACTIVITY)
        val menuInflater = MenuInflater(APP_ACTIVITY)
        menuInflater.inflate(menu, menuBuilder)
        val optionsMenu = MenuPopupHelper(APP_ACTIVITY, menuBuilder, view)
        optionsMenu.setForceShowIcon(true)

        menuBuilder.setCallback((object : MenuBuilder.Callback {
            override fun onMenuItemSelected(menu: MenuBuilder, item: MenuItem): Boolean {
                return when (item.itemId) {
                    menuItemId -> {
                        toast(message)
                        viewModel.deleteSingleNote(note)
                        true
                    }
                    else -> false
                }
            }

            override fun onMenuModeChange(menu: MenuBuilder) {}
        }))
        optionsMenu.show()
    }
}