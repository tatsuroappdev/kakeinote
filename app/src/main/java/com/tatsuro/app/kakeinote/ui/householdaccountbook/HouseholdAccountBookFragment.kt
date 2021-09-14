package com.tatsuro.app.kakeinote.ui.householdaccountbook

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.constant.IncomeOrExpense
import com.tatsuro.app.kakeinote.databinding.HouseholdAccountBookFragmentBinding
import com.tatsuro.app.kakeinote.ui.edit.EditActivity
import com.tatsuro.app.kakeinote.ui.getViewModel
import com.tatsuro.app.kakeinote.ui.householdaccountbook.adapter.HouseholdAccountBookHeaderAdapter
import com.tatsuro.app.kakeinote.ui.householdaccountbook.adapter.HouseholdAccountBookListAdapter
import com.tatsuro.app.kakeinote.ui.setOnSafeClickListener

/** 家計簿フラグメント */
class HouseholdAccountBookFragment : Fragment(R.layout.household_account_book_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = getViewModel(HouseholdAccountBookViewModel::class.java)

        val binding = HouseholdAccountBookFragmentBinding.bind(view).also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }.apply {
            TooltipCompat.setTooltipText(
                prevMonthButton, getString(R.string.previous_month)
            )
            TooltipCompat.setTooltipText(
                nextMonthButton, getString(R.string.next_month)
            )
            TooltipCompat.setTooltipText(
                writeButton, getString(R.string.show_new_write_activity)
            )

            writeButton.setOnSafeClickListener {
                val intent = Intent(requireContext(), EditActivity::class.java)
                startActivity(intent)
            }
        }

        viewModel.householdAccountBook.observe(viewLifecycleOwner) { householdAccountBookList ->
            Logger.d(householdAccountBookList)

            // 家計簿リストからデータが存在する日付リストを取得する。
            val dateList = householdAccountBookList.map { it.date }.distinct()

            val concatAdapter = ConcatAdapter()

            // 家計簿は日毎に表示する。
            for (date in dateList) {
                val dailyHouseholdAccountBook =
                    householdAccountBookList.filter { it.date == date }

                // 日毎のヘッダ
                val dailyIncomeAmountSum = dailyHouseholdAccountBook
                    .filter { it.incomeOrExpense == IncomeOrExpense.INCOME }
                    .map { it.amountOfMoney }
                    .sum()
                val dailyExpenseAmountSum = dailyHouseholdAccountBook
                    .filter { it.incomeOrExpense == IncomeOrExpense.EXPENSE }
                    .map { it.amountOfMoney }
                    .sum()
                concatAdapter.addAdapter(
                    HouseholdAccountBookHeaderAdapter(
                        date, dailyIncomeAmountSum, dailyExpenseAmountSum)
                )

                // 日毎の家計簿本体
                concatAdapter.addAdapter(
                    HouseholdAccountBookListAdapter(dailyHouseholdAccountBook) {
                    }
                )
            }

            binding.householdAccountBookRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = concatAdapter
            }
        }
    }
}
