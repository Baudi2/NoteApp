package ru.startandroid.develop.notebook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import ru.startandroid.develop.notebook.databinding.DarkModeChooserDialogBinding
import ru.startandroid.develop.notebook.utils.AppThemeModes

class DarkModeChooserDialog : DialogFragment() {

    private var binding: DarkModeChooserDialogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DarkModeChooserDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initViews() {
        binding?.let { binding ->
            with(binding) {
                darkModeChooserCancelButton.setOnClickListener { dismiss() }
                darkModeChooserApplyButton.setOnClickListener { getSelectedMode() }
            }
        }
    }

    private fun getSelectedMode() {
        binding?.let { binding ->
            with(binding) {
                if (darkModeChooserDarkModeButton.isChecked) returnSelectedMode(AppThemeModes.DARK_MODE)
                if (darkModeChooserLightModeButton.isChecked) returnSelectedMode(AppThemeModes.LIGHT_MODE)
                if (darkModeChooserSystemAdaptButton.isChecked) returnSelectedMode(AppThemeModes.SYSTEM_ADAPT)
            }
        }
    }

    private fun returnSelectedMode(mode: AppThemeModes) {
        val bundle = bundleOf(DARK_MODE_CHOOSER_DIALOG_MODE_KEY to mode)
        setFragmentResult(DARK_MODE_CHOOSER_DIALOG_RESULT_KEY, bundle)
        dismiss()
    }

    companion object {

        const val DARK_MODE_CHOOSER_DIALOG_MODE_KEY = "DARK_MODE_CHOOSER_DIALOG_MODE_KEY"
        const val DARK_MODE_CHOOSER_DIALOG_RESULT_KEY = "DARK_MODE_CHOOSER_DIALOG_MODE_KEY"
    }
}