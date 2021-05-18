package com.tatsuro.app.kakeinote.database

import androidx.room.TypeConverter
import com.tatsuro.app.kakeinote.constant.IncomeOrExpense
import com.tatsuro.app.kakeinote.constant.IncomeOrExpenseType
import java.time.LocalDate
import java.time.LocalTime

class Converters {

    /**
     * [LocalDate]型変数を[String]型変数に変換して返す。
     * @param value 変換前の[LocalDate]型変数
     * @return 変換後の[String]型変数
     */
    @TypeConverter
    fun localDateToString(value: LocalDate) = value.toString()

    /**
     * [String]型変数を[LocalDate]型変数にパースして返す。
     * @param value 変換前の[String]型変数
     * @return 変換後の[LocalDate]型変数
     */
    @TypeConverter
    fun stringToLocalDate(value: String): LocalDate = LocalDate.parse(value)

    /**
     * [LocalTime]型変数を[String]型変数に変換して返す。
     * @param value 変換前の[LocalTime]型変数
     * @return 変換後の[String]型変数
     */
    @TypeConverter
    fun localTimeToString(value: LocalTime) = value.toString()

    /**
     * [String]型変数を[LocalTime]型変数にパースして返す。
     * @param value 変換前の[String]型変数
     * @return 変換後の[LocalTime]型変数
     */
    @TypeConverter
    fun stringToLocalTime(value: String): LocalTime = LocalTime.parse(value)

    /** 収支の[String]型定数名を返す。 */
    @TypeConverter
    fun incomeOrExpenseToString(value: IncomeOrExpense) = value.name

    /** [String]型収支を[IncomeOrExpense]型に変換して返す。 */
    @TypeConverter
    fun stringToIncomeOrExpense(value: String) = enumValueOf<IncomeOrExpense>(value)

    /** 収支の種類の[String]型定数名を返す。 */
    @TypeConverter
    fun incomeOrExpenseTypeToString(value: IncomeOrExpenseType?) = value?.name

    /** [String]型収支の種類を[IncomeOrExpenseType]型に変換して返す。 */
    @TypeConverter
    fun stringToIncomeOrExpenseType(value: String?): IncomeOrExpenseType? {
        val nonNullValue = value ?: return null
        return enumValueOf<IncomeOrExpenseType>(nonNullValue)
    }
}
