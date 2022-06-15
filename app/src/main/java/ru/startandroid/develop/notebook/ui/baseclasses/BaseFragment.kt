package ru.startandroid.develop.notebook.ui.baseclasses

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        keyboardOpenListener(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        keyboardOpenListener(false)
    }

    open fun keyboardVisibilityListener(isOpen: Boolean) {}

    private fun keyboardOpenListener(isListening: Boolean) {
        if (isListening) binding?.root?.viewTreeObserver?.addOnGlobalLayoutListener(viewTreeObserver)
        else binding?.root?.viewTreeObserver?.removeOnGlobalLayoutListener(viewTreeObserver)
    }
}