package ru.startandroid.develop.notebook.ui.customclasses

import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import ru.startandroid.develop.notebook.R
import ru.startandroid.develop.notebook.databinding.TimedActionSnackbarLayoutBinding

class TimedSnackBar(private val view: View) {

    private var binding: TimedActionSnackbarLayoutBinding? = null
    private var snackBar: Snackbar? = null
    private var timer: CountDownTimer? = null

    fun showSnackBar(
        title: String,
        cancelMessage: String,
        duration: Long,
        cancelAction: () -> Unit
    ) {
        initBinding()
        setupSnackBar(duration.toInt() - MILLIS_IN_SECOND.toInt())
        initViews(title, cancelMessage, (duration / MILLIS_IN_SECOND).toString(), cancelAction)
        setupTimer(duration)
        snackBar?.show()
    }

    private fun initBinding() {
        val snackView = View.inflate(view.context, R.layout.timed_action_snackbar_layout, null)
        binding = TimedActionSnackbarLayoutBinding.bind(snackView)
    }

    private fun setupSnackBar(duration: Int) {
        snackBar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE)
        snackBar?.let { snackBar ->
            (snackBar.view as ViewGroup).removeAllViews()
            (snackBar.view as ViewGroup).addView(binding?.root)
            snackBar.setDuration(duration)
        }
    }

    private fun initViews(
        title: String,
        cancelMessage: String,
        durationText: String,
        cancelAction: () -> Unit,
    ) {
        binding?.let { binding ->
            with(binding) {
                timedSnackbarTitle.text = title
                timedSnackbarAction.setOnClickListener {
                    cancelAction.invoke()
                    tearDown()
                }
                timedSnackbarAction.text = cancelMessage
                timedSnackbarTimer.text = durationText
            }
        }
    }

    private fun setupTimer(duration: Long) {
        binding?.let { binding ->
            timer = object : CountDownTimer(duration, MILLIS_IN_SECOND) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.timedSnackbarTimer.text = (millisUntilFinished / MILLIS_IN_SECOND).toString()
                }

                override fun onFinish() {}
            }.start()
        }
    }

    private fun tearDown() {
        snackBar?.dismiss()
        binding = null
        snackBar = null
        timer?.cancel()
        timer = null
    }

    companion object {

        private const val MILLIS_IN_SECOND = 1000L

        const val LENGTH_LONG = 8000L
        const val LENGTH_SHORT = 5005L
    }
}