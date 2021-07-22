package com.tatsuro.app.kakeinote.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import java.time.LocalDate

@Dao
interface Dao {

    /**
     * [start]以上[end]未満の家計簿を取得する。
     * @param start 家計簿取得期間の開始日。
     * @param end 家計簿取得期間の終了日。ただし、[end]当日は取得期間に含まれない。
     * @return 取得した家計簿
     */
    @Query(
        "SELECT * FROM household_account_book " +
            "WHERE date >= :start AND date < :end ORDER BY date DESC, time DESC"
    )
    fun select(start: LocalDate, end: LocalDate): LiveData<List<HouseholdAccountBook>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(householdAccountBook: HouseholdAccountBook)

    @Delete
    suspend fun delete(householdAccountBook: HouseholdAccountBook)
}
