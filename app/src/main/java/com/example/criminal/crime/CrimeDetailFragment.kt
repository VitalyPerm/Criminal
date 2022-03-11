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

private const val ARG_CRIME_ID = "crime_id"

class CrimeFragment : Fragment(R.layout.fragment_crime_detail) {

    private val crimeDetailViewModel: CrimeDetailViewModel by lazy {
        ViewModelProvider(this)[CrimeDetailViewModel::class.java]
    }

    private val binding by viewBinding(FragmentCrimeDetailBinding::bind)

    private var editableCrime: Crime? = null


    companion object {
        fun newInstance(crimeId: UUID) = CrimeFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        crimeDetailViewModel.getCrime(crimeId).observe(viewLifecycleOwner) {
            it?.let { crime ->
                editableCrime = crime
                binding.run {
                    crimeTitle.setText(crime.title)

                    crimeDate.text = crime.date.toString()

                    crimeSolved.isChecked = crime.isSolved

                    crimeTitle.doOnTextChanged { text, start, before, count ->
                        crime.title = text.toString()
                        editableCrime?.let { editableCrime -> editableCrime.title = text.toString() }
                    }

                    crimeSolved.apply {
                        setOnCheckedChangeListener { _, isChecked ->
                            crime.isSolved = isChecked
                            jumpDrawablesToCurrentState()
                            editableCrime?.let { editableCrime -> editableCrime.isSolved = isChecked }
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
