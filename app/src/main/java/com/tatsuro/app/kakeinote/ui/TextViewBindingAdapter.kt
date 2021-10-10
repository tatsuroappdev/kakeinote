package com.tatsuro.app.kakeinote.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tatsuro.app.kakeinote.App
import com.tatsuro.app.kakeinote.R
import java.time.LocalDate
import kotlin.math.abs

/** テキストビューのバインディングアダプタ */
@Suppress("unused")
object TextViewBindingAdapter {

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
     * [amountOfMoney]に¥マークとカンマの3桁区切りを付けて、[TextView]に設定する。
     * @param textView 設定先の[TextView]
     * @param amountOfMoney 金額
     */
    @BindingAdapter("amountOfMoney")
    @JvmStatic
    fun intToAmountOfMoneyString(textView: TextView, amountOfMoney: Int) {
        textView.text = "¥ %,d".format(amountOfMoney)
    }

    /**
     * [income]と[expense]の和の絶対値を求め、その値に¥マークとカンマの3桁区切りを付けて、[TextView]に設定する。
     * @param textView 設定先の[TextView]
     * @param income 収入金額
     * @param expense 支出金額
     */
    @BindingAdapter("income", "expense")
    @JvmStatic
    fun incomeAndExpenseToSumOfMoneyString(textView: TextView, income: Int, expense: Int) {
        val sum = income - expense

        val id = if (sum >= 0) {
            R.color.blue
        } else {
            R.color.red
        }

        textView.apply {
            setTextColor(App.getColor(id))
            text = "¥ %,d".format(abs(sum))
        }
    }
}
