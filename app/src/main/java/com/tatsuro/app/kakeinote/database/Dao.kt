package com.tatsuro.app.kakeinote.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface Dao {

//    @Query("SELECT * FROM household_account_book")
//    suspend fun select(): LiveData<List<HouseholdAccountBook>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(householdAccountBook: HouseholdAccountBook)

    @Delete
    suspend fun delete(householdAccountBook: HouseholdAccountBook)
}
