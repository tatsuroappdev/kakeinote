package com.tatsuro.app.kakeinote.ui.householdaccountbook

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tatsuro.app.kakeinote.R

/** 家計簿Fragment. */
class HouseholdAccountBookFragment : Fragment(R.layout.household_account_book_fragment) {

    companion object {
        fun newInstance() = HouseholdAccountBookFragment()
    }

    private val viewModel: HouseholdAccountBookViewModel by viewModels()
}