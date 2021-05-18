package com.tatsuro.app.kakeinote.constant

import com.tatsuro.app.kakeinote.R

/**
 * 収支
 * @property strResId ローカル文字列のリソースID
 */
enum class IncomeOrExpense(val strResId: Int) {

    /** 収入 */
    INCOME(R.string.income),

    /** 支出 */
    EXPENSE(R.string.expense);

    /** 収支の種類の配列 */
    val incomeOrExpenseTypes get() =
        when(this) {
            INCOME -> {
                arrayOf(
                    IncomeOrExpenseType.SALARY,
                    IncomeOrExpenseType.PENSION,
                    IncomeOrExpenseType.REMITTANCE,
                    IncomeOrExpenseType.POCKET_MONEY,
                    IncomeOrExpenseType.BONUS,
                    IncomeOrExpenseType.TEMPORARY_INCOME,
                    IncomeOrExpenseType.LOAN,
                    IncomeOrExpenseType.INTEREST,
                    IncomeOrExpenseType.INVESTMENT_INCOME,
                    IncomeOrExpenseType.SALE_INCOME,
                    IncomeOrExpenseType.OTHER_INCOME
                )
            }
            EXPENSE -> {
                arrayOf(
                    IncomeOrExpenseType.FOOD_EXPENSE,
                    IncomeOrExpenseType.LEISURE,
                    IncomeOrExpenseType.ENTERTAINMENT,
                    IncomeOrExpenseType.CLOTHING,
                    IncomeOrExpenseType.BEAUTY,
                    IncomeOrExpenseType.BOOK,
                    IncomeOrExpenseType.LIBERAL_ARTS,
                    IncomeOrExpenseType.MEDICAL_CARE,
                    IncomeOrExpenseType.HEALTH_CARE,
                    IncomeOrExpenseType.TRANSPORTATION,
                    IncomeOrExpenseType.HOUSING,
                    IncomeOrExpenseType.WATER,
                    IncomeOrExpenseType.GAS,
                    IncomeOrExpenseType.ELECTRICITY,
                    IncomeOrExpenseType.COMMUNICATION,
                    IncomeOrExpenseType.TAX,
                    IncomeOrExpenseType.OTHER_EXPENSE
                )
            }
        }
}
