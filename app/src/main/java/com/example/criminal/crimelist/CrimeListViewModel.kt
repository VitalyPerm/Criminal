package com.example.criminal.crimelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.criminal.db.Crime
import com.example.criminal.db.CrimeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CrimeListViewModel : ViewModel() {

    private val crimeRepository = CrimeRepository.getRepository()
    val crimesLiveData = crimeRepository.getCrimes()

    private var addCrimeJob: Job? = null

    fun addCrime(crime: Crime) {
        addCrimeJob?.cancel()
        addCrimeJob = viewModelScope.launch {
            try {
                crimeRepository.addCrime(crime)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        addCrimeJob?.cancel()
    }

}