package com.tatsuro.app.kakeinote.ui.householdaccountbook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.constant.ErrorMessages
import com.tatsuro.app.kakeinote.constant.IncomeOrExpense
import com.tatsuro.app.kakeinote.databinding.HouseholdAccountBookFragmentBinding
import com.tatsuro.app.kakeinote.ui.details.DetailsActivity
import com.tatsuro.app.kakeinote.ui.householdaccountbook.adapter.HouseholdAccountBookHeaderAdapter
import com.tatsuro.app.kakeinote.ui.householdaccountbook.adapter.HouseholdAccountBookListAdapter

/** 家計簿フラグメント */
class HouseholdAccountBookFragment : Fragment(R.layout.household_account_book_fragment) {

    /** 家計簿リスト項目がクリックされたときに呼び出されるコールバックのためのインターフェース定義 */
    interface OnItemClickListener {

        /**
         * 家計簿リスト項目がクリックされたときに呼び出される。
         * @param id クリックされた家計簿ID
         */
        fun onItemClick(id: Int)
    }

    /** 家計簿ビューモデル */
    private val viewModel: HouseholdAccountBookViewModel by viewModels()

    /** 家計簿リスト項目のクリックリスナ */
    private lateinit var onItemClickListener: OnItemClickListener

    /**
     * 引数[context]にオーバーライドされているクリックイベントメソッドをクリックリスナに設定する。
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnItemClickListener) {
            onItemClickListener = context
        } else {
            error(ErrorMessages.DO_NOT_INHERIT_ON_ITEM_CLICK_LISTENER)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

            writeButton.setOnClickListener {
                val intent = Intent(requireContext(), DetailsActivity::class.java)
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
                        onItemClickListener.onItemClick(it)
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
