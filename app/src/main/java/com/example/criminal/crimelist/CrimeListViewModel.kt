package com.example.criminal.crimelist

import androidx.lifecycle.ViewModel
import com.example.criminal.db.Crime
import com.example.criminal.db.CrimeRepository

class CrimeListViewModel : ViewModel() {

    private val crimeRepository = CrimeRepository.getRepository()
    val crimesLiveData = crimeRepository.getCrimes()

    fun addCrime(crime: Crime) {
        crimeRepository.addCrime(crime)
    }

}