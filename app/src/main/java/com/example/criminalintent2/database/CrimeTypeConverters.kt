package com.example.criminalintent2.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

class CrimeTypeConverters {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }


}
