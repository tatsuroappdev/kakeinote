package com.tatsuro.app.kakeinote.constant

/** 関数[error]に渡すエラーメッセージ */
object ErrorMessages {

    /** アプリケーションインスタンスが取得されていない。 */
    const val APPLICATION_INSTANCE_NOT_GOTTEN =
        "The application instance is not gotten."

    /** NavHostFragmentへのキャストに失敗した。 */
    const val FAILED_TO_CAST_NAV_HOST_FRAGMENT =
        "Failed to cast to NavHostFragment."

    /** コンテキストはOnWriteButtonClickListenerを継承していない。 */
    const val DO_NOT_INHERIT_ON_WRITE_BUTTON_CLICK_LISTENER =
        "The context doesn't inherit OnWriteButtonClickListener."

    /** コンテキストはOnItemClickListenerを継承していない。 */
    const val DO_NOT_INHERIT_ON_ITEM_CLICK_LISTENER =
        "The context doesn't inherit OnItemClickListener."

    /** データバインディングがバインドされていない。 */
    const val DATA_BINDING_NOT_BOUND =
        "The data binding is not bound."

    /** ビューバインディングがバインドされていない。 */
    const val VIEW_BINDING_NOT_BOUND =
        "The view binding is not bound."

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
