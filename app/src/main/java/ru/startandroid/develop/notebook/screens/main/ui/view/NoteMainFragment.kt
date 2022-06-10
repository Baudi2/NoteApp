package ru.startandroid.develop.notebook.screens.main.ui.view

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.startandroid.develop.notebook.R
import ru.startandroid.develop.notebook.core.AppThemeModes
import ru.startandroid.develop.notebook.core.extensions.showKeyboard
import ru.startandroid.develop.notebook.databinding.NoteMainFragmentBinding
import ru.startandroid.develop.notebook.screens.global.model.NoteUi
import ru.startandroid.develop.notebook.screens.main.ui.adapter.NoteAdapter
import ru.startandroid.develop.notebook.screens.main.ui.adapter.NoteItemClickListener
import ru.startandroid.develop.notebook.screens.main.ui.presentation.NoteMainViewModel
import ru.startandroid.develop.notebook.screens.main.ui.view.DarkModeChooserDialog.Companion.DARK_MODE_CHOOSER_DIALOG_MODE_KEY
import ru.startandroid.develop.notebook.screens.main.ui.view.DarkModeChooserDialog.Companion.DARK_MODE_CHOOSER_DIALOG_RESULT_KEY
import ru.startandroid.develop.notebook.ui.customclasses.TimedSnackBar

@AndroidEntryPoint
class NoteMainFragment : Fragment(), NoteItemClickListener {

    private var binding: NoteMainFragmentBinding? = null

    private val viewModel by viewModels<NoteMainViewModel>()

    private val noteAdapter: NoteAdapter by lazy { NoteAdapter(this) }

    // возможно вынести в базовый класс
    private val viewTreeObserver = ViewTreeObserver.OnGlobalLayoutListener {
        val rect = Rect()
        binding?.root?.getWindowVisibleDisplayFrame(rect)
        val screenHeight: Int = binding?.root?.rootView?.height ?: 0
        val keypadHeight: Int = screenHeight - rect.bottom

        if (keypadHeight > screenHeight * 0.15) {
            // keyboard is opened
            if (!isKeyboardShowing) {
                isKeyboardShowing = true
                keyboardVisibilityListener(true)
            }
        } else {
            // keyboard is closed
            if (isKeyboardShowing) {
                isKeyboardShowing = false
                keyboardVisibilityListener(false)
            }
        }
    }

    private var isKeyboardShowing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = NoteMainFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NoteMainFragmentBinding.bind(view)
        setHasOptionsMenu(true)
        initViews()
        collectNotes()
        initRecyclerView()
        modeChooserResultListener()
        keyboardOpenListener(true)
    }

    override fun onItemClick(note: NoteUi) {
        val action =
            NoteMainFragmentDirections.actionNoteMainFragmentToNoteAddEditFragment(
                getString(R.string.change), note
            )
        findNavController().navigate(action)
    }

    override fun onPopupClick(note: NoteUi, view: View) {
        showPopup(view, R.menu.main_menu_popup, R.id.delete_one_item, note)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.main_menu_delete -> {
                displayDeleteAllNotesDialog()
                true
            }
            R.id.main_menu_dark_mode -> {
                getCurrentAppThemeMode()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notesState.collect {
                    menu.findItem(R.id.main_menu_delete).isEnabled = it.isNotEmpty() == true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        keyboardOpenListener(false)
        binding = null
    }

    private fun initViews() {
        binding?.let { binding ->
            with(binding) {
                noteMainFabAddNote.setOnClickListener {
                    navigateToAdd()
                }
                noteMainClearSearch.setOnClickListener { noteMainSearchEditText.setText("") }
                noteMainSearchEditText.addTextChangedListener { query ->
                    noteMainClearSearch.isVisible = !query.isNullOrEmpty()
                    onSearchQueryChanged(query.toString())
                }
                noteMainSearchLayout.setOnClickListener {
                    noteMainSearchEditText.requestFocus()
                    requireContext().showKeyboard(noteMainSearchEditText)
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding?.noteMainRecyclerView?.apply {
            adapter = noteAdapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }
    }

    private fun keyboardVisibilityListener(isOpen: Boolean) {
        binding?.let { binding ->
            with(binding) {
                noteMainFabAddNote.isVisible = !isOpen
            }
        }
    }

    private fun collectNotes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notesState.collect { displayNotes(it) }
            }
        }
    }

    private fun displayNotes(notes: List<NoteUi>) {
        // TODO: this text blinks when adding new item & when updating item and opening the app
        binding?.noteMainNoNotes?.isVisible = notes.isEmpty()
        if (notes.isEmpty()) binding?.noteMainNoNotes?.text = getString(R.string.no_created_notes)
        noteAdapter.submitList(notes)
    }

    private fun onSearchQueryChanged(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notesState.collect { notes ->
                    searchItem(notes, query)
                }
            }
        }
    }

    private fun searchItem(notes: List<NoteUi>, query: String) {
        notes.filter { filteredNotes ->
            filteredNotes.header.lowercase().contains(query.lowercase())
        }.let { searchedNotes ->
            binding?.noteMainNoNotes?.isVisible = searchedNotes.isEmpty()
            if (searchedNotes.isEmpty()) {
                binding?.noteMainNoNotes?.text =
                    getString(R.string.empty_search_result_message)
            }
            noteAdapter.submitList(searchedNotes)
        }
    }

    private fun navigateToAdd() {
        val action =
            NoteMainFragmentDirections.actionNoteMainFragmentToNoteAddEditFragment(
                getString(R.string.create_a_note),
                null
            )
        findNavController().navigate(action)
    }

    private fun displayDeleteAllNotesDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.attention))
            .setMessage(getString(R.string.confirm_delete_all_notes_message))
            .setPositiveButton(getString(R.string.delete_all_notes)) { _, _ ->
                showUndoDeleteAllNotesSnackBar(viewModel.notesState.value)
                viewModel.deleteAllNotesFromDb()
            }.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            .show()
    }

    private fun getCurrentAppThemeMode() {
        viewModel.getUserSavedThemeMode()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentMode.collect { mode ->
                    if (mode != null) {
                        showDarkModeDialog(mode)
                        viewModel.currentMode.value = null
                    }
                }
            }
        }
    }

    private fun showDarkModeDialog(mode: AppThemeModes) {
        val action = NoteMainFragmentDirections
            .actionNoteMainFragmentToDarkModeChooserDialog(mode)
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
        viewModel.saveUserSelectedThemeMode(mode)
        when (mode) {
            AppThemeModes.DARK_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            AppThemeModes.LIGHT_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            AppThemeModes.SYSTEM_ADAPT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun showPopup(
        view: View, menu: Int, menuItemId: Int, note: NoteUi
    ) {
        val menuBuilder = MenuBuilder(requireContext())
        MenuInflater(requireContext()).inflate(menu, menuBuilder)
        val optionsMenu = MenuPopupHelper(requireContext(), menuBuilder, view)
        optionsMenu.setForceShowIcon(true)
        menuBuilder.setCallback((object : MenuBuilder.Callback {
            override fun onMenuItemSelected(menu: MenuBuilder, item: MenuItem) =
                when (item.itemId) {
                    menuItemId -> {
                        viewModel.deleteSingleNote(note)
                        showUndoNoteDeleteSnackBar(note)
                        true
                    }
                    else -> false
                }

            override fun onMenuModeChange(menu: MenuBuilder) {}
        }))
        optionsMenu.show()
    }

    private fun showUndoNoteDeleteSnackBar(note: NoteUi) {
        binding?.let { binding ->
            TimedSnackBar(binding.root).showSnackBar(
                getString(R.string.cancel_note_deletion_message),
                getString(R.string.undo),
                TimedSnackBar.LENGTH_SHORT
            ) { viewModel.insertNote(note) }
        }
    }

    private fun showUndoDeleteAllNotesSnackBar(notes: List<NoteUi>) {
        binding?.let { binding ->
            TimedSnackBar(binding.root).showSnackBar(
                getString(R.string.cancel_all_notes_deletion_message),
                getString(R.string.undo),
                TimedSnackBar.LENGTH_LONG
            ) { viewModel.insertAllNotes(notes) }
        }
    }

    private fun keyboardOpenListener(isListening: Boolean) {
        if (isListening) binding?.root?.viewTreeObserver?.addOnGlobalLayoutListener(viewTreeObserver)
        else binding?.root?.viewTreeObserver?.removeOnGlobalLayoutListener(viewTreeObserver)
    }
}