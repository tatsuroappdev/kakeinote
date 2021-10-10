package com.tatsuro.app.kakeinote.ui.widget

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.InverseBindingListener
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseBindingMethods
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.constant.IncomeOrExpense
import com.tatsuro.app.kakeinote.databinding.IncomeOrExpenseToggleButtonBinding
import com.tatsuro.app.kakeinote.ui.getColorStateList

/** 収支トグルボタン */
@InverseBindingMethods(
    InverseBindingMethod(
        type = IncomeOrExpenseToggleButton::class,
        attribute = "incomeOrExpense"
    )
)
class IncomeOrExpenseToggleButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    companion object {

        /** 収支のデフォルト値 */
        val defaultIncomeOrExpense = IncomeOrExpense.EXPENSE
    }

    /** 収支 */
    private var _incomeOrExpense = defaultIncomeOrExpense
        set(value) {
            if (value == field) {
                return
            }
            field = value
            listener?.onChange()
            setBackgroundTintList(_incomeOrExpense)
        }

    private var listener: InverseBindingListener? = null

    private val binding: IncomeOrExpenseToggleButtonBinding =
        IncomeOrExpenseToggleButtonBinding.inflate(LayoutInflater.from(context), this)

    init {

        // ボタン背景色を初期化する。
        setBackgroundTintList(_incomeOrExpense)

        binding.apply {
            incomeButton.setOnClickListener {
                _incomeOrExpense = IncomeOrExpense.INCOME
            }

            expenseButton.setOnClickListener {
                _incomeOrExpense = IncomeOrExpense.EXPENSE
            }
        }
    }

    fun getIncomeOrExpense() = _incomeOrExpense

    fun setIncomeOrExpense(value: IncomeOrExpense) {
        _incomeOrExpense = value
    }

    fun setInverseBindingListener(listener: InverseBindingListener) {
        this.listener = listener
    }

    /** [incomeOrExpense]に合わせてボタンの背景色を設定する。 */
    private fun setBackgroundTintList(incomeOrExpense: IncomeOrExpense) {
        binding.apply {
            when (incomeOrExpense) {
                IncomeOrExpense.INCOME -> {
                    incomeButton.backgroundTintList =
                        getColorStateList(R.color.bg_income_button_on)
                    expenseButton.backgroundTintList =
                        getColorStateList(R.color.bg_expense_button_off)
                }
                IncomeOrExpense.EXPENSE -> {
                    incomeButton.backgroundTintList =
                        getColorStateList(R.color.bg_income_button_off)
                    expenseButton.backgroundTintList =
                        getColorStateList(R.color.bg_expense_button_on)
                }
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val parent = super.onSaveInstanceState()
        val savedState = SavedState(parent)
        savedState.incomeOrExpense = _incomeOrExpense
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            _incomeOrExpense = state.incomeOrExpense
        }
        super.onRestoreInstanceState(state)
    }

    private class SavedState(superState: Parcelable?) : BaseSavedState(superState) {
        var incomeOrExpense = defaultIncomeOrExpense
    }
}
