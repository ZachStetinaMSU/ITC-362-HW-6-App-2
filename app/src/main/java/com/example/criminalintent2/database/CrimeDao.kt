package com.example.criminalintent2.database

import androidx.room.Query
import androidx.room.Update
import com.example.criminalintent2.Crime
import kotlinx.coroutines.flow.Flow
import java.util.UUID


interface CrimeDao {
    @Query("SELECT * FROM crime")
     fun getCrimes(): Flow<List<Crime>>

    @Query("SELECT * FROM crime WHERE id=(:id)")
    suspend fun getCrime(id: UUID): Crime

    @Update
    suspend fun updateCrime(crime: Crime)

}


