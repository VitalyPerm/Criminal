package com.example.criminal.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {

    private val database: CrimeDataBase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDataBase::class.java,
        DATABASE_NAME
    )
        .addMigrations(CrimeDataBase.migration_1_2)
        .build()


    private val crimeDao = database.crimeDao()

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()

    suspend fun getCrime(id: UUID): Crime = withContext(Dispatchers.IO) {
        return@withContext crimeDao.getCrime(id) ?: Crime()
    }

    suspend fun updateCrime(crime: Crime) = withContext(Dispatchers.IO) {
        crimeDao.updateCrime(crime)
    }


    suspend fun addCrime(crime: Crime) = withContext(Dispatchers.IO) {
        crimeDao.addCrime(crime)
    }


    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun getRepository(): CrimeRepository = INSTANCE ?: throw IllegalStateException("FUCK PUTIN")
    }
}