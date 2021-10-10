package com.tatsuro.app.kakeinote.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tatsuro.app.kakeinote.constant.IncomeOrExpense
import com.tatsuro.app.kakeinote.constant.IncomeOrExpenseType
import java.time.LocalDate
import java.time.LocalTime

/** 家計簿エンティティ */
@Entity(tableName = "household_account_book")
data class HouseholdAccountBook(

    /** ID */
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    /** 日付 */
    var date: LocalDate = LocalDate.now(),

    /** 時間 */
    var time: LocalTime = LocalTime.now(),

    /** 収支 */
    @ColumnInfo(name = "income_or_expense")
    var incomeOrExpense: IncomeOrExpense = IncomeOrExpense.EXPENSE,

    /** 収支の種類 */
    var type: IncomeOrExpenseType = IncomeOrExpenseType.OTHER_EXPENSE,

    /** 内容 */
    var content: String = "",

    /** 金額 */
    @ColumnInfo(name = "amount_of_money")
    var amountOfMoney: Int = 0,

    /** メモ */
    var memo: String = ""
)
