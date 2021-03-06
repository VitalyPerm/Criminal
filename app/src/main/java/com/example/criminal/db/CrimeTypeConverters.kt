package com.example.criminal.db

import androidx.room.TypeConverter
import java.util.*

class CrimeTypeConverters {

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(ms: Long?): Date? = ms?.let { Date(it) }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? = UUID.fromString(uuid)

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? = uuid?.toString()
}