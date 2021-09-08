package com.tatsuro.app.kakeinote.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
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

    /** 金額 */
    val amountOfMoney = MutableLiveData<Int?>()

    /** 家計簿ライブデータ */
    val householdAccountBookLiveData = MediatorLiveData<HouseholdAccountBook>()

    /** 家計簿 */
    val householdAccountBook get() = householdAccountBookLiveData
        .value ?: error(ErrorMessages.HOUSEHOLD_ACCOUNT_BOOK_LIVEDATA_NOT_INITIALIZED)

    init {
        householdAccountBookLiveData.value = HouseholdAccountBook()
        householdAccountBookLiveData.addSource(amountOfMoney) { nullable ->
            nullable?.let { nonNull ->
                householdAccountBook.amountOfMoney = nonNull
                householdAccountBookLiveData.value = householdAccountBook
            }
        }
    }

    /** エポックミリ秒の日付 */
    var dateAtEpochMilli: Long
        get() = householdAccountBook.date
                .atStartOfDay(zoneOffset)
                .toInstant()
                .toEpochMilli()
        set(dateAtEpochMilli) {
            val dateTime = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(dateAtEpochMilli), zoneOffset)

            householdAccountBook.date = LocalDate.from(dateTime)
            householdAccountBookLiveData.value = householdAccountBook
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
        .getInstance(application)
        .dao()

    /** 家計簿の初期化する。 */
    fun initHouseholdAccountBook() {
        householdAccountBookLiveData.value = HouseholdAccountBook()
    }

    /**
     * 時間を設定する。
     * @param hour 時間
     * @param minute 分
     * @exception IllegalStateException [householdAccountBookLiveData]が初期化されていない場合に投げられる。
     */
    fun setTime(hour: Int, minute: Int) {
        // 時分を設定する。
        // withメソッドはインスタンスを書き換えないため、 そのメソッドの戻り値で上書きする。
        householdAccountBook.time = householdAccountBook.time.withHour(hour).withMinute(minute)
        householdAccountBookLiveData.value = householdAccountBook
    }

    /**
     * 収支の種類を設定する。LiveData
     * @param type 収支の種類
     * @exception IllegalStateException [householdAccountBookLiveData]が初期化されていない場合に投げられる。
     */
    fun setIncomeOrExpenseType(type: IncomeOrExpenseType) {
        householdAccountBook.type = type
        householdAccountBookLiveData.value = householdAccountBook
    }

    /**
     * 支出ボタンのイベント
     *
     * 支出ボタンが押されたとき、家計簿の収支を支出に設定する。また、収支の種類をnull値に初期化する。
     * @exception IllegalStateException [householdAccountBookLiveData]が初期化されていない場合に投げられる。
     */
    fun onExpenseButtonClick() {
        if (householdAccountBook.incomeOrExpense == IncomeOrExpense.EXPENSE) {
            return
        }

        householdAccountBook.incomeOrExpense = IncomeOrExpense.EXPENSE
        householdAccountBook.type = null
        householdAccountBookLiveData.value = householdAccountBook
    }

    /**
     * 収入ボタンのイベント
     *
     * 収入ボタンが押されたとき、家計簿の収支を収入に設定する。また、収支の種類をnull値に初期化する。
     * @exception IllegalStateException [householdAccountBookLiveData]が初期化されていない場合に投げられる。
     */
    fun onIncomeButtonClick() {
        if (householdAccountBook.incomeOrExpense == IncomeOrExpense.INCOME) {
            return
        }

        householdAccountBook.incomeOrExpense = IncomeOrExpense.INCOME
        householdAccountBook.type = null
        householdAccountBookLiveData.value = householdAccountBook
    }

    /**
     * 前の日ボタンのイベント
     * @exception IllegalStateException [householdAccountBookLiveData]が初期化されていない場合に投げられる。
     */
    fun onPrevDayButtonClick() {
        // minusDaysメソッドはインスタンスを書き換えないため、 そのメソッドの戻り値で上書きする。
        householdAccountBook.date = householdAccountBook.date.minusDays(1)
        householdAccountBookLiveData.value = householdAccountBook
    }

    /**
     * 次の日ボタンのイベント
     * @exception IllegalStateException [householdAccountBookLiveData]が初期化されていない場合に投げられる。
     */
    fun onNextDayButtonClick() {
        // plusDaysメソッドはインスタンスを書き換えないため、 そのメソッドの戻り値で上書きする。
        householdAccountBook.date = householdAccountBook.date.plusDays(1)
        householdAccountBookLiveData.value = householdAccountBook
    }

    /**
     * 家計簿に収支を1件書き込む。
     * @exception IllegalStateException [householdAccountBookLiveData]が初期化されていない場合に投げられる。
     */
    suspend fun upsert() {
        dao.upsert(householdAccountBook)
    }
}
