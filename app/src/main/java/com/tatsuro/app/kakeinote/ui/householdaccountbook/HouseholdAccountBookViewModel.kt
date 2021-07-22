package com.tatsuro.app.kakeinote.ui.householdaccountbook

import android.app.Application
import androidx.lifecycle.*
import com.tatsuro.app.kakeinote.constant.ErrorMessages
import com.tatsuro.app.kakeinote.database.AppDatabase
import java.time.LocalDate

/** 家計簿ビューモデル */
class HouseholdAccountBookViewModel(application: Application) : AndroidViewModel(application) {

    /** 選択年月（内部書き込み向け） */
    private val _selectedYearMonth =
        MutableLiveData(LocalDate.now().withDayOfMonth(1))

    /** 選択年月 */
    val selectedYearMonth: LiveData<LocalDate> get() = _selectedYearMonth

    /** DAO */
    private val dao = AppDatabase
        .getInstance(application)
        .dao()

    /** 選択年月の家計簿 */
    val householdAccountBook = selectedYearMonth.switchMap { start ->
        val end = start.plusMonths(1)
        dao.select(start, end)
    }

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
