package com.tatsuro.app.kakeinote.ui.householdaccountbook

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.TooltipCompat
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

    /** 書き込むボタンがクリックされたときに呼び出されるコールバックのためのインターフェース定義 */
    interface OnWriteButtonClickListener {

        /** 書き込むボタンがクリックされたときに呼び出される。 */
        fun onWriteButtonClick()
    }

    /** 家計簿リストの行がクリックされたときに呼び出されるコールバックのためのインターフェース定義 */
    interface OnItemClickListener {

        /**
         * 家計簿リストの行がクリックされたときに呼び出される。
         * @param id クリックされた家計簿ID
         */
        fun onItemClick(id: Int)
    }

    private val viewModel: HouseholdAccountBookViewModel by viewModels()

    private lateinit var onWriteButtonClickListener: OnWriteButtonClickListener

    private lateinit var onItemClickListener: OnItemClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnWriteButtonClickListener) {
            onWriteButtonClickListener = context
        } else {
            // TODO:要実装
            error("")
        }

        if (context is OnItemClickListener) {
            onItemClickListener = context
        } else {
            // TODO:要実装
            error("")
        }
    }

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
                        onItemClickListener.onItemClick(it)
                    }
                )
            }

            binding.apply {
                householdAccountBookRecyclerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = concatAdapter
                }

                TooltipCompat.setTooltipText(
                    writeButton, getString(R.string.show_new_write_activity))

                writeButton.setOnClickListener {
                    onWriteButtonClickListener.onWriteButtonClick()
                }
            }
        }
    }
}
