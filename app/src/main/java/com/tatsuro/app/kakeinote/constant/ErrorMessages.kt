package com.tatsuro.app.kakeinote.constant

/** 関数[error]に渡すエラーメッセージ */
object ErrorMessages {

    /** アプリケーションインスタンスが取得されていない。 */
    const val APPLICATION_INSTANCE_NOT_GOTTEN =
        "The application instance is not gotten."

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
