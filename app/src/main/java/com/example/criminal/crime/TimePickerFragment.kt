package com.example.criminal.crime

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*


class TimePickerFragment : DialogFragment() {

    private var onDateSelected: ((String) -> Unit)? = null

    companion object {
        fun newInstance(onDateSelected: (String) -> Unit): TimePickerFragment {
            return TimePickerFragment().apply {
                this.onDateSelected = onDateSelected
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()
        val initialMinute = calendar.get(Calendar.MINUTE)
        val initialHour = calendar.get(Calendar.HOUR)

        val listener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            val minuteFormatted = if(minute < 10) "0$minute" else minute
            onDateSelected?.invoke("$hour:$minuteFormatted")
        }

        return TimePickerDialog(
            requireContext(),
            listener,
            initialMinute,
            initialHour,
            true
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDateSelected = null
    }
}