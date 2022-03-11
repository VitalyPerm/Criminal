package com.example.criminal.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Crime::class], version = 1, exportSchema = false)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDataBase: RoomDatabase() {

    abstract fun crimeDao(): CrimeDao
}