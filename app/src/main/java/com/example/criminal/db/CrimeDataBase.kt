package com.example.criminal.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Crime::class], version = 3, exportSchema = false)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDataBase : RoomDatabase() {

    abstract fun crimeDao(): CrimeDao

    companion object {
        val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE Crime ADD COLUMN suspect TEXT NOT NULL DEFAULT ''"
                )
                database.execSQL(
                    "ALTER TABLE Crime ADD COLUMN time TEXT NOT NULL DEFAULT ''"
                )
            }
        }
        val migration_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE Crime ADD COLUMN phone TEXT NOT NULL DEFAULT ''"
                )
            }
        }
    }
}