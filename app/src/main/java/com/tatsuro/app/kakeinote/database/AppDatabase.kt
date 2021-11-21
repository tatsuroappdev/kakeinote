package com.tatsuro.app.kakeinote.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/** アプリケーションデータベース */
@Database(entities = [HouseholdAccountBook::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    /** DAO */
    abstract fun dao(): Dao
}
