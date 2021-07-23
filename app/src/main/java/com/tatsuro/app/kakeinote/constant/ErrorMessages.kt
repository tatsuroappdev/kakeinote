package com.tatsuro.app.kakeinote.constant

/** 関数[error]に渡すエラーメッセージ */
object ErrorMessages {

    /** 無効な項目が選択されている。 */
    const val INVALID_ITEM_SELECTED =
        "The invalid item is selected."

    /** 無効なフラグメントが選択されている。 */
    const val INVALID_FRAGMENT_SELECTED =
        "The invalid fragment is selected."

    /** コンテキストはOnWriteButtonClickListenerを継承していない。 */
    const val DO_NOT_INHERIT_ON_WRITE_BUTTON_CLICK_LISTENER =
        "The context doesn't inherit OnWriteButtonClickListener."

    /** コンテキストはOnItemClickListenerを継承していない。 */
    const val DO_NOT_INHERIT_ON_ITEM_CLICK_LISTENER =
        "The context doesn't inherit OnItemClickListener."

    /** プロパティselectedYearMonthは初期化されていない。 */
    const val SELECTED_YEAR_MONTH_NOT_INITIALIZED =
        "The property selectedYearMonth is not initialized."

    /** データバインディングがバインドされていない。 */
    const val DATA_BINDING_NOT_BOUND =
        "The data binding is not bound."

    /** プロパティviewModelは初期化されていない。 */
    const val VIEW_MODEL_NOT_INITIALIZED =
        "The property viewModel is not initialized."

    /** プロパティhouseholdAccountBookは初期化されていない。 */
    const val HOUSEHOLD_ACCOUNT_BOOK_NOT_INITIALIZED =
        "The property householdAccountBook is not initialized."

    /** 変数incomeOrExpenseは不明である。 */
    const val INCOME_OR_EXPENSE_UNKNOWN =
        "The variable incomeOrExpense is unknown."
}
