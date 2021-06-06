package com.tatsuro.app.kakeinote.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    @Query("SELECT * FROM household_account_book ORDER BY date DESC, time DESC")
    fun selectAtLiveData(): LiveData<List<HouseholdAccountBook>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(householdAccountBook: HouseholdAccountBook)

    @Delete
    suspend fun delete(householdAccountBook: HouseholdAccountBook)
}
