package com.example.criminal.crime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.criminal.db.Crime
import com.example.criminal.db.CrimeRepository
import java.util.*

class CrimeDetailViewModel : ViewModel() {

    private val crimeRepository = CrimeRepository.getRepository()

    fun getCrime(id: UUID) = crimeRepository.getCrime(id)

    fun saveCrime(crime: Crime) = crimeRepository.updateCrime(crime)

}