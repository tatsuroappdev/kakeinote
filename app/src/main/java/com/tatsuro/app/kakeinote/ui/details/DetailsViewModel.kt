package com.tatsuro.app.kakeinote.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.material.datepicker.MaterialDatePicker
import com.tatsuro.app.kakeinote.constant.ErrorMessages
import com.tatsuro.app.kakeinote.constant.IncomeOrExpense
import com.tatsuro.app.kakeinote.constant.IncomeOrExpenseType
import com.tatsuro.app.kakeinote.database.AppDatabase
import com.tatsuro.app.kakeinote.database.HouseholdAccountBook
import java.time.*

/** 詳細ビューモデル */
class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    /** 家計簿 */
    val householdAccountBook = MutableLiveData(HouseholdAccountBook())

    /** エポックミリ秒の日付 */
    var dateAtEpochMilli: Long
        get() {
            val value = householdAccountBook
                .value ?: error(ErrorMessages.HOUSEHOLD_ACCOUNT_BOOK_NOT_INITIALIZED)

            return value.date
                .atStartOfDay(zoneOffset)
                .toInstant()
                .toEpochMilli()
        }
        set(dateAtEpochMilli) {
            val dateTime = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(dateAtEpochMilli), zoneOffset)

            val value = householdAccountBook
                .value ?: error(ErrorMessages.HOUSEHOLD_ACCOUNT_BOOK_NOT_INITIALIZED)
            value.date = LocalDate.from(dateTime)

            householdAccountBook.value = value
        }

    /**
     * タイムゾーンオフセット
     *
     * [MaterialDatePicker]がUTCで動作するため、それに合わせる。
     *
     * [#546](https://github.com/material-components/material-components-android/issues/546)
     */
    private val zoneOffset = ZoneOffset.UTC

    /** DAO */
    private val dao = AppDatabase
        .getInstance(application.applicationContext)
        .dao()

    /** 家計簿の初期化する。 */
    fun initHouseholdAccountBook() {
        householdAccountBook.value = HouseholdAccountBook()
    }

    /**
     * 時間を設定する。
     * @param hour 時間
     * @param minute 分
     * @exception IllegalStateException プロパティhouseholdAccountBookが初期化されていない場合に投げられる。
     */
    fun setTime(hour: Int, minute: Int) {
        val value = householdAccountBook
            .value ?: error(ErrorMessages.HOUSEHOLD_ACCOUNT_BOOK_NOT_INITIALIZED)

        // 時分を設定する。
        // withメソッドはインスタンスを書き換えないため、 そのメソッドの戻り値で上書きする。
        value.time = value.time.withHour(hour).withMinute(minute)

        householdAccountBook.value = value
    }

    /**
     * 収支の種類を設定する。
     * @param type 収支の種類
     */
    fun setIncomeOrExpenseType(type: IncomeOrExpenseType) {
        val value = householdAccountBook
            .value ?: error(ErrorMessages.HOUSEHOLD_ACCOUNT_BOOK_NOT_INITIALIZED)
        value.type = type
        householdAccountBook.value = value
    }

    /**
     * 支出ボタンのイベント
     *
     * 支出ボタンが押されたとき、家計簿の収支を支出に設定する。また、収支の種類をnull値に初期化する。
     * @exception IllegalStateException プロパティhouseholdAccountBookが初期化されていない場合に投げられる。
     */
    fun onExpenseButtonClick() {
        val value = householdAccountBook
            .value ?: error(ErrorMessages.HOUSEHOLD_ACCOUNT_BOOK_NOT_INITIALIZED)

        if (value.incomeOrExpense == IncomeOrExpense.EXPENSE) {
            return
        }

        value.incomeOrExpense = IncomeOrExpense.EXPENSE
        value.type = null
        householdAccountBook.value = value
    }

    /**
     * 収入ボタンのイベント
     *
     * 収入ボタンが押されたとき、家計簿の収支を収入に設定する。また、収支の種類をnull値に初期化する。
     * @exception IllegalStateException プロパティhouseholdAccountBookが初期化されていない場合に投げられる。
     */
    fun onIncomeButtonClick() {
        val value = householdAccountBook
            .value ?: error(ErrorMessages.HOUSEHOLD_ACCOUNT_BOOK_NOT_INITIALIZED)

        if (value.incomeOrExpense == IncomeOrExpense.INCOME) {
            return
        }

        value.incomeOrExpense = IncomeOrExpense.INCOME
        value.type = null
        householdAccountBook.value = value
    }

    /**
     * 前の日ボタンのイベント
     * @exception IllegalStateException プロパティhouseholdAccountBookが初期化されていない場合に投げられる。
     */
    fun onPrevDayButtonClick() {
        val value = householdAccountBook
            .value ?: error(ErrorMessages.HOUSEHOLD_ACCOUNT_BOOK_NOT_INITIALIZED)

        // minusDaysメソッドはインスタンスを書き換えないため、 そのメソッドの戻り値で上書きする。
        value.date = value.date.minusDays(1)

        householdAccountBook.value = value
    }

    /**
     * 次の日ボタンのイベント
     * @exception IllegalStateException プロパティhouseholdAccountBookが初期化されていない場合に投げられる。
     */
    fun onNextDayButtonClick() {
        val value = householdAccountBook
            .value ?: error(ErrorMessages.HOUSEHOLD_ACCOUNT_BOOK_NOT_INITIALIZED)

        // plusDaysメソッドはインスタンスを書き換えないため、 そのメソッドの戻り値で上書きする。
        value.date = value.date.plusDays(1)

        householdAccountBook.value = value
    }

    /**
     * 家計簿に収支を1件書き込む。
     * @exception IllegalStateException プロパティhouseholdAccountBookが初期化されていない場合に投げられる。
     */
    suspend fun upsert() {
        val value = householdAccountBook
            .value ?: error(ErrorMessages.HOUSEHOLD_ACCOUNT_BOOK_NOT_INITIALIZED)
        dao.upsert(value)
    }

    /**
     * @exception IllegalStateException プロパティhouseholdAccountBookが初期化されていない場合に投げられる。
     */
    suspend fun delete() {
        val value = householdAccountBook
            .value ?: error(ErrorMessages.HOUSEHOLD_ACCOUNT_BOOK_NOT_INITIALIZED)
        dao.delete(value)
    }
}
