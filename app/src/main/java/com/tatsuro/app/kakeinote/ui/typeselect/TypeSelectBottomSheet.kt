package com.tatsuro.app.kakeinote.ui.typeselect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.constant.ErrorMessages
import com.tatsuro.app.kakeinote.constant.IncomeOrExpense
import com.tatsuro.app.kakeinote.databinding.TypeSelectBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

/** 種類選択ボトムシート */
@AndroidEntryPoint
class TypeSelectBottomSheet : BottomSheetDialogFragment() {

    companion object {

        const val EXTRA_ENUM_SELECTED_TYPE =
            "com.tatsuro.app.kakeinote.EXTRA_ENUM_SELECTED_TYPE"

        /** 引数収支 */
        private const val ARG_INCOME_OR_EXPENSE = "incomeOrExpense"

        /** 引数リクエストキー */
        private const val ARG_REQUEST_KEY = "requestKey"

        /**
         * フラグメントのインスタントを返す。
         * @param incomeOrExpense 収支
         * @param requestKey 選択された種類を受け渡すためのリクエストキー
         * @return フラグメントインスタント
         */
        fun newInstance(incomeOrExpense: IncomeOrExpense, requestKey: String) =
            TypeSelectBottomSheet().apply {
                arguments = bundleOf(
                    ARG_INCOME_OR_EXPENSE to incomeOrExpense,
                    ARG_REQUEST_KEY to requestKey
                )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.type_select_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = TypeSelectBottomSheetBinding.bind(view)

        val incomeOrExpense = requireArguments().getSerializable(ARG_INCOME_OR_EXPENSE)
        val requestKey = requireArguments().getString(ARG_REQUEST_KEY)

        if (incomeOrExpense !is IncomeOrExpense) {
            error(ErrorMessages.INCOME_OR_EXPENSE_UNKNOWN)
        }

        binding.apply {
            toolbar.setNavigationOnClickListener {
                dismiss()
            }

            typeRecyclerView.apply {
                layoutManager = GridLayoutManager(
                    requireContext(), 2, RecyclerView.VERTICAL, false)
                adapter = TypeAdapter(incomeOrExpense.incomeOrExpenseTypes) { type ->
                    requestKey?.let { key ->
                        // 選択された種類を親フラグメントに返す。
                        setFragmentResult(
                            key, bundleOf(
                                EXTRA_ENUM_SELECTED_TYPE to type
                            )
                        )
                    }

                    dismiss()
                }
            }
        }
    }
}
