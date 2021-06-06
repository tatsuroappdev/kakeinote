package com.tatsuro.app.kakeinote.ui.householdaccountbook

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.constant.IncomeOrExpense
import com.tatsuro.app.kakeinote.databinding.HouseholdAccountBookFragmentBinding

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = HouseholdAccountBookFragmentBinding.bind(view)

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
                        date, dailyIncomeAmountSum, dailyExpenseAmountSum))

                // 日毎の家計簿本体
                concatAdapter.addAdapter(
                    HouseholdAccountBookBodyAdapter(dailyHouseholdAccountBook) {
                        // TODO:要実装
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
