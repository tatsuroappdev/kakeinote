package com.tatsuro.app.kakeinote.ui.widget.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import com.tatsuro.app.kakeinote.ui.widget.IncomeOrExpenseToggleButton

@Suppress("unused")
object IncomeOrExpenseToggleButtonBindingAdapter {

    @BindingAdapter("incomeOrExpenseAttrChanged")
    @JvmStatic
    fun setListener(view: IncomeOrExpenseToggleButton, listener: InverseBindingListener) {
        view.setInverseBindingListener(listener)
    }
}
