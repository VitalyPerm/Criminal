package com.example.criminal

import androidx.lifecycle.ViewModel
import com.example.criminal.db.CrimeRepository

class CrimeListViewModel : ViewModel() {

    private val crimeRepository = CrimeRepository.getRepository()
    val crimesLiveData = crimeRepository.getCrimes()

}