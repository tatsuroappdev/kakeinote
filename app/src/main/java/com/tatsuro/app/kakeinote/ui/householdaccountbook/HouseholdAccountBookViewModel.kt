package com.tatsuro.app.kakeinote.ui.householdaccountbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tatsuro.app.kakeinote.database.AppDatabase

/** 家計簿ビューモデル */
class HouseholdAccountBookViewModel(application: Application) : AndroidViewModel(application) {

//    val householdAccountBookFragment = dao.select()

    /** DAO */
    private val dao = AppDatabase
        .getInstance(application.applicationContext)
        .dao()
}
