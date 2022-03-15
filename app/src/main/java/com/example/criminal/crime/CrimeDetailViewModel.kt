package com.example.criminal.crime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.criminal.db.Crime
import com.example.criminal.db.CrimeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class CrimeDetailViewModel : ViewModel() {

    private val crimeRepository = CrimeRepository.getRepository()

    private val _crime = MutableLiveData<Crime>()

    private var getCrimeJob: Job? = null

    private var saveCrimeJob: Job? = null

    val crime get() = _crime

    fun getCrime(id: UUID) {
        getCrimeJob?.cancel()

        getCrimeJob = viewModelScope.launch {
            try {
                _crime.value = crimeRepository.getCrime(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveCrime(crime: Crime) {
        saveCrimeJob?.cancel()
        saveCrimeJob = viewModelScope.launch {
            try {
                crimeRepository.updateCrime(crime)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveCrimeJob?.cancel()
        getCrimeJob?.cancel()
    }

}