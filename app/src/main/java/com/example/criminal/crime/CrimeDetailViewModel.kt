package com.example.criminal.crime

import androidx.lifecycle.ViewModel
import com.example.criminal.db.CrimeRepository
import java.util.*

class CrimeDetailViewModel : ViewModel(){

    private val crimeRepository = CrimeRepository.getRepository()

    fun getCrime(id: UUID) = crimeRepository.getCrime(id)

}