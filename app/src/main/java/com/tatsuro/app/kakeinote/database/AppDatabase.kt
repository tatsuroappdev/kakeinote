package com.tatsuro.app.kakeinote.database

import android.content.Context
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
         * @param context コンテキスト
         * @return データベースインスタンス
         */
        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "AppDatabase.db")
                        .build()
                }
                return instance!!
            }
        }
    }
}
