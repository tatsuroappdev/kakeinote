package com.tatsuro.app.kakeinote.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/** アプリケーションデータベース */
@Database(entities = [HouseholdAccountBook::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    /** DAO */
    abstract fun dao(): Dao

    companion object {

        /** データベースインスタンス */
        private var instance: AppDatabase? = null

        /** ロックインスタンス */
        private val lock = Any()

        /**
         * データベースインスタンスを返す。
         * @param application アプリケーション
         * @return データベースインスタンス
         */
        fun getInstance(application: Application): AppDatabase {
            synchronized(lock) {
                if (instance == null) {
                    instance = Room.databaseBuilder(application.applicationContext,
                        AppDatabase::class.java, "AppDatabase.db")
                        .build()
                }
                return instance!!
            }
        }
    }
}
