package com.tatsuro.app.kakeinote.ui.householdaccountbook

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tatsuro.app.kakeinote.R

/** 家計簿フラグメント */
class HouseholdAccountBookFragment : Fragment(R.layout.household_account_book_fragment) {

    companion object {

        /**
         * フラグメントのインスタントを返す。
         * @return フラグメントインスタント
         */
        fun newInstance() = HouseholdAccountBookFragment()
    }

    private val viewModel: HouseholdAccountBookViewModel by viewModels()
}
