package com.example.criminal.crime

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.format.DateFormat
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
private const val REQUEST_CONTACT = 1
private const val DATE_FORMAT = "EEE, MMM, dd"

class CrimeDetailFragment : Fragment(R.layout.fragment_crime_detail) {

    private val vm: CrimeDetailViewModel by lazy {
        ViewModelProvider(this)[CrimeDetailViewModel::class.java]
    }

    private val binding by viewBinding(FragmentCrimeDetailBinding::bind)

    private lateinit var editableCrime: Crime


    companion object {
        const val ARG_CRIME_ID = "crime_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID

        vm.getCrime(crimeId)

        vm.crime.observe(viewLifecycleOwner) {
            it?.let { crime ->
                editableCrime = crime
                binding.run {
                    crimeTitle.setText(crime.title)

                    crimeDate.text = getDate(crime.date)

                    crimeSolved.isChecked = crime.isSolved

                    crimeTime.text = "Time: "

                    if (crime.suspect.isNotEmpty()) {
                        crimeSuspect.text = crime.suspect
                    }

                    crimeTitle.doOnTextChanged { text, _, _, _ ->
                        editableCrime.title = text.toString()
                    }


                }
            }
        }
        setClickListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            resultCode != Activity.RESULT_OK -> return
            requestCode == REQUEST_CONTACT && data != null -> {
                val contactUri: Uri? = data.data
                val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
                val cursor = contactUri?.let {
                    requireActivity().contentResolver
                        .query(it, queryFields, null, null, null)
                }
                cursor?.use {
                    if (it.count == 0) {
                        return
                    }
                    it.moveToFirst()
                    val suspect = it.getString(0)
                    editableCrime.suspect = suspect
                    vm.saveCrime(editableCrime)
                    binding.crimeSuspect.text = suspect
                }
            }
        }
    }

    private fun setClickListeners() {
        binding.run {

            crimeReport.setOnClickListener {
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, getCrimeReport(editableCrime))
                    putExtra(
                        Intent.EXTRA_SUBJECT,
                        getString(R.string.crime_report_subject)
                    )
                }.also { intent ->
                    val chooserIntent =
                        Intent.createChooser(intent, getString(R.string.send_report))
                    startActivity(chooserIntent)
                }
            }

            crimeSuspect.apply {
                val pickContactIntent =
                    Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                setOnClickListener {
                    startActivityForResult(pickContactIntent, REQUEST_CONTACT)
                }
            }

            crimeSolved.apply {
                setOnCheckedChangeListener { _, isChecked ->
                    jumpDrawablesToCurrentState()
                    editableCrime.isSolved = isChecked
                }
            }

            crimeTime.setOnClickListener {
                TimePickerFragment.newInstance { time ->
                    crimeTime.text = "Time: $time"
                    editableCrime.time = time
                }.apply {
                    show(this@CrimeDetailFragment.parentFragmentManager, "")
                }
            }

            crimeDate.setOnClickListener {
                DatePickerFragment.newInstance(editableCrime.date) { date ->
                    crimeDate.text = getDate(date)
                    editableCrime.date = date
                }.apply {
                    show(this@CrimeDetailFragment.parentFragmentManager, DIALOG_DATE)
                }
            }

        }
    }

    private fun getCrimeReport(crime: Crime): String {
        val solvedString = if (crime.isSolved) {
            getString(R.string.crime_report_solved)
        } else {
            getString(R.string.crime_report_unsolved)
        }
        val dateString = DateFormat.format(DATE_FORMAT, crime.date).toString()
        val suspect = if (crime.suspect.isBlank()) {
            getString(R.string.crime_report_no_suspect)
        } else {
            getString(R.string.crime_report_suspect, crime.suspect)
        }
        return getString(
            R.string.crime_report,
            crime.title, dateString, solvedString, suspect
        )
    }


    override fun onStop() {
        super.onStop()
        vm.saveCrime(editableCrime)
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
