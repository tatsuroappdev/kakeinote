package com.tatsuro.app.kakeinote.constant

/** 関数[error]に渡すエラーメッセージ */
object ErrorMessages {

    /** 無効な項目が選択されている。 */
    const val INVALID_ITEM_SELECTED =
        "The invalid item is selected."

    /** 無効なフラグメントが選択されている。 */
    const val INVALID_FRAGMENT_SELECTED =
        "The invalid fragment is selected."

    /** プロパティselectedYearMonthは初期化されていない。 */
    const val SELECTED_YEAR_MONTH_NOT_INITIALIZED =
        "The property selectedYearMonth is not initialized."

    /** プロパティhouseholdAccountBookLiveDataは初期化されていない。 */
    const val HOUSEHOLD_ACCOUNT_BOOK_LIVEDATA_NOT_INITIALIZED =
        "The property householdAccountBookLiveData is not initialized."

    /** 変数incomeOrExpenseは不明である。 */
    const val INCOME_OR_EXPENSE_UNKNOWN =
        "The variable incomeOrExpense is unknown."
}
