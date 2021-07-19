package com.tatsuro.app.kakeinote.ui.householdaccountbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tatsuro.app.kakeinote.constant.ErrorMessages
import com.tatsuro.app.kakeinote.database.AppDatabase
import java.time.LocalDate

/** 家計簿ビューモデル */
class HouseholdAccountBookViewModel(application: Application) : AndroidViewModel(application) {

    /** 選択年月（内部書き込み向け） */
    private val _selectedYearMonth = MutableLiveData(LocalDate.now())

    /** 選択年月 */
    val selectedYearMonth: LiveData<LocalDate> get() = _selectedYearMonth

    val householdAccountBook = AppDatabase
        .getInstance(application.applicationContext)
        .dao()
        .selectAtLiveData()

    /** 前月ボタンのクリックイベント */
    fun onPrevMonthButtonClick() {
        val value = selectedYearMonth
            .value ?: error(ErrorMessages.SELECTED_YEAR_MONTH_NOT_INITIALIZED)

        // minusMonthsメソッドはインスタンスを書き換えないため、 そのメソッドの戻り値で上書きする。
        _selectedYearMonth.value = value.minusMonths(1)
    }

    /** 次月ボタンのクリックイベント */
    fun onNextMonthButtonClick() {
        val value = selectedYearMonth
            .value ?: error(ErrorMessages.SELECTED_YEAR_MONTH_NOT_INITIALIZED)

        // plusMonthsメソッドはインスタンスを書き換えないため、 そのメソッドの戻り値で上書きする。
        _selectedYearMonth.value = value.plusMonths(1)
    }
}
