package com.tatsuro.app.kakeinote.ui

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tatsuro.app.kakeinote.App
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.constant.IncomeOrExpenseType
import java.time.LocalDate
import java.time.LocalTime

/** バインディングアダプタ */
@Suppress("unused")
object BindingAdapters {

    /**
     * [LocalDate]型日付から年月を取り出し、[TextView]に設定する。
     * @param textView 設定先の[TextView]
     * @param date [TextView]に設定する年月を取り出す。
     */
    @BindingAdapter("yearMonth")
    @JvmStatic
    fun dateToYearMonthString(textView: TextView, date: LocalDate) {
        val context = App.applicationContext
        textView.text = context.getString(
            R.string.formatted_year_month,
            date.year,
            date.monthValue
        )
    }

    /**
     * [LocalDate]型日付を[String]型に変換して、[EditText]に設定する。
     * @param editText 設定先の[EditText]
     * @param date [EditText]に設定する日付
     */
    @BindingAdapter("date")
    @JvmStatic
    fun dateToString(editText: EditText, date: LocalDate) {
        val context = App.applicationContext
        val dayOfWeek = context.resources
            .getStringArray(R.array.day_of_week)[date.dayOfWeek.ordinal]
        editText.setText(context.getString(
            R.string.formatted_date,
            date.year,
            date.monthValue,
            date.dayOfMonth,
            dayOfWeek
        ))
    }

    /**
     * [LocalTime]型時間を[String]型に変換して、[EditText]に設定する。
     * このとき、時分は2桁0埋めする。
     * @param editText 設定先の[EditText]
     * @param time [EditText]に表示する時間
     */
    @BindingAdapter("time")
    @JvmStatic
    fun timeToString(editText: EditText, time: LocalTime) {
        val text = "%02d:%02d".format(time.hour, time.minute)
        editText.setText(text)
    }

    /**
     * 収支の種類を[EditText]に設定する。
     * @param editText 設定先の[EditText]
     * @param type 収支の種類
     */
    @BindingAdapter("incomeOrExpenseType")
    @JvmStatic
    fun incomeOrExpenseTypeToString(editText: EditText, type: IncomeOrExpenseType?) {
        if (type == null) {
            editText.setText("")
        } else {
            editText.setText(type.strResId)
        }
    }
}
