package com.tatsuro.app.kakeinote.di

import android.content.Context
import androidx.room.Room
import com.tatsuro.app.kakeinote.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context,
    ) = Room.databaseBuilder(appContext, AppDatabase::class.java, "AppDatabase.db")
        .build()

    @Singleton
    @Provides
    fun provideDao(database: AppDatabase) = database.dao()
}
