package com.tatsuro.app.kakeinote.ui.main

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tatsuro.app.kakeinote.constant.ErrorMessages
import com.tatsuro.app.kakeinote.ui.householdaccountbook.HouseholdAccountBookFragment
import com.tatsuro.app.kakeinote.ui.misc.MiscBaseFragment
import com.tatsuro.app.kakeinote.ui.search.SearchFragment

class BottomNavigationViewAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    companion object {

        /** 家計簿フラグメントID */
        const val HOUSEHOLD_ACCOUNT_BOOK_FRAGMENT_ID = 0

        /** 検索フラグメントID */
        const val SEARCH_FRAGMENT_ID = 1

        /** その他ベースフラグメントID */
        const val MISC_BASE_FRAGMENT_ID = 2
    }

    override fun getItemCount() = 3

    override fun createFragment(position: Int) = when(position) {
        HOUSEHOLD_ACCOUNT_BOOK_FRAGMENT_ID -> HouseholdAccountBookFragment()
        SEARCH_FRAGMENT_ID -> SearchFragment()
        MISC_BASE_FRAGMENT_ID -> MiscBaseFragment()
        else -> error(ErrorMessages.INVALID_FRAGMENT_SELECTED)
    }
}
