package com.tatsuro.app.kakeinote.ui.details

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.tatsuro.app.kakeinote.App
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.constant.ErrorMessages
import com.tatsuro.app.kakeinote.constant.IncomeOrExpense
import com.tatsuro.app.kakeinote.constant.IncomeOrExpenseType
import com.tatsuro.app.kakeinote.database.AppDatabase
import com.tatsuro.app.kakeinote.database.HouseholdAccountBook
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

    /** 種類未選択イベント */
    val typeNotSelectedEvent: SharedFlow<HouseholdAccountBook>
        get() = rewritableTypeNotSelectedEvent

    /** アクティビティ終了イベント */
    val activityFinishEvent: SharedFlow<Any>
        get() = rewritableActivityFinishEvent

    /** 書き込み完了イベント */
    val writeCompleteEvent: SharedFlow<HouseholdAccountBook>
        get() = rewritableWriteCompleteEvent

    /** 種類未選択イベント（書き換え可能） */
    private val rewritableTypeNotSelectedEvent = MutableSharedFlow<HouseholdAccountBook>()

    /** アクティビティ終了イベント（書き換え可能） */
    private val rewritableActivityFinishEvent = MutableSharedFlow<Any>()

    /** 書き込み完了イベント（書き換え可能） */
    private val rewritableWriteCompleteEvent = MutableSharedFlow<HouseholdAccountBook>()

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
     * 収支の種類を設定する。
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
     * 書き込むボタンのクリックイベント
     * @param view クリックされたボタンビュー
     * @exception IllegalStateException [householdAccountBookLiveData]が初期化されていない場合に投げられる。
     */
    fun onOnceWriteButtonClick(view: View) {
        if (householdAccountBook.type == null) {
            viewModelScope.launch {
                rewritableTypeNotSelectedEvent.emit(householdAccountBook)
            }
            return
        }

        // ボタンを無効化する。
        view.apply {
            isClickable = false
            setBackgroundColor(App.getColor(R.color.translucent_light_blue))
        }

        // データベースに保存する。
        runBlocking {
            upsert()
        }

        viewModelScope.launch {
            rewritableActivityFinishEvent.emit(Any())
        }
    }

    /**
     * 連続で書き込むボタンのクリックイベント
     * @param view クリックされたボタンビュー
     * @exception IllegalStateException [householdAccountBookLiveData]が初期化されていない場合に投げられる。
     */
    fun onRepeatWriteButtonClick(view: View) {
        if (householdAccountBook.type == null) {
            viewModelScope.launch {
                rewritableTypeNotSelectedEvent.emit(householdAccountBook)
            }
            return
        }

        // ボタンを無効化する。
        view.apply {
            isClickable = false
            setBackgroundColor(App.getColor(R.color.translucent_light_blue))
        }

        // データベースに保存する。
        runBlocking {
            upsert()
        }

        viewModelScope.launch {
            rewritableWriteCompleteEvent.emit(householdAccountBook)
        }

        // 家計簿を初期化する。
        householdAccountBookLiveData.value = HouseholdAccountBook()

        // ボタンを有効化する。
        view.apply {
            isClickable = true
            setBackgroundColor(App.getColor(R.color.light_blue))
        }
    }

    /**
     * 家計簿に収支を1件書き込む。
     * @exception IllegalStateException [householdAccountBookLiveData]が初期化されていない場合に投げられる。
     */
    private suspend fun upsert() {
        dao.upsert(householdAccountBook)
    }
}
