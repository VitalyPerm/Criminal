package com.example.criminal.crime

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.criminal.R
import com.example.criminal.databinding.FragmentCrimeDetailBinding
import com.example.criminal.db.Crime
import java.util.*


private const val DIALOG_DATE = "DialogDate"

class CrimeDetailFragment : Fragment(R.layout.fragment_crime_detail) {

    private val crimeDetailViewModel: CrimeDetailViewModel by lazy {
        ViewModelProvider(this)[CrimeDetailViewModel::class.java]
    }

    private val binding by viewBinding(FragmentCrimeDetailBinding::bind)

    private var editableCrime: Crime? = null


    companion object {
        const val ARG_CRIME_ID = "crime_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID

        crimeDetailViewModel.getCrime(crimeId).observe(viewLifecycleOwner) {
            it?.let { crime ->
                editableCrime = crime
                binding.run {
                    crimeTitle.setText(crime.title)

                    crimeDate.text = getDate(crime.date)

                    crimeSolved.isChecked = crime.isSolved

                    crimeTime.text = "Time: "

                    crimeTime.setOnClickListener {
                        TimePickerFragment.newInstance { time ->
                            crimeTime.text = "Time: $time"
                        }.apply {
                            show(this@CrimeDetailFragment.parentFragmentManager, "")
                        }
                    }

                    crimeTitle.doOnTextChanged { text, start, before, count ->
                        editableCrime?.let { editableCrime ->
                            editableCrime.title = text.toString()
                        }
                    }

                    crimeDate.setOnClickListener {
                        DatePickerFragment.newInstance(crime.date) { date ->
                            crimeDate.text = getDate(date)
                        }.apply {
                            show(this@CrimeDetailFragment.parentFragmentManager, DIALOG_DATE)
                        }
                    }

                    crimeSolved.apply {
                        setOnCheckedChangeListener { _, isChecked ->
                            jumpDrawablesToCurrentState()
                            editableCrime?.let { editableCrime ->
                                editableCrime.isSolved = isChecked
                            }
                        }
                    }
                }
            }
        }
    }


    override fun onStop() {
        super.onStop()
        editableCrime?.let { crimeDetailViewModel.saveCrime(it) }
    }
}

fun getDate(date: Date): String {
    val calendar = Calendar.getInstance()
    calendar.time = date
    return "Date: ${calendar.get(Calendar.DAY_OF_MONTH)} / ${calendar.get(Calendar.MONTH)} / ${
        calendar.get(
            Calendar.YEAR
        )
    }"
}
