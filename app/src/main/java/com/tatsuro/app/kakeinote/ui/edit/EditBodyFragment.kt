package com.tatsuro.app.kakeinote.ui.edit

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.orhanobut.logger.Logger
import com.tatsuro.app.kakeinote.App
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.constant.ErrorMessages
import com.tatsuro.app.kakeinote.constant.IncomeOrExpense
import com.tatsuro.app.kakeinote.constant.IncomeOrExpenseType
import com.tatsuro.app.kakeinote.databinding.EditBodyFragmentBinding
import com.tatsuro.app.kakeinote.ui.setOnSafeClickListener
import com.tatsuro.app.kakeinote.ui.typeselect.TypeSelectBottomSheet
import kotlinx.coroutines.flow.collect

/** 編集ボディフラグメント */
class EditBodyFragment : Fragment(R.layout.edit_body_fragment) {

    companion object {

        /** 種類選択ボトムシートから選択された種類を取得するためのリクエストキー */
        private const val REQUEST_KEY_TYPE_SELECT = "typeSelect"
    }

    /** バインディングインスタンスの実体 */
    private var nullableBinding: EditBodyFragmentBinding? = null

    /** 読み取り専用バインディング */
    private val readOnlyBinding get() =
        nullableBinding ?: error(ErrorMessages.DATA_BINDING_NOT_BOUND)

    private val _viewModel: EditViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nullableBinding = EditBodyFragmentBinding.bind(view).apply {
            viewModel = _viewModel
            lifecycleOwner = viewLifecycleOwner

            // 日付EditTextがクリックされたとき、日付変更ダイアログを表示する。
            dateEditText.setOnSafeClickListener {
                val datePicker = MaterialDatePicker.Builder
                    .datePicker()
                    .setSelection(_viewModel.dateAtEpochMilli)
                    .build().apply {
                        addOnPositiveButtonClickListener {
                            _viewModel.dateAtEpochMilli = it
                        }
                    }

                datePicker.show(parentFragmentManager, datePicker.toString())
            }

            // 時間EditTextがクリックされたとき、時間変更ダイアログを表示する。
            timeEditText.setOnSafeClickListener {
                val timePicker = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setTitleText(R.string.please_select_time)
                    .setHour(_viewModel.householdAccountBook.time.hour)
                    .setMinute(_viewModel.householdAccountBook.time.minute)
                    .build()

                timePicker.addOnPositiveButtonClickListener {
                    _viewModel.setTime(timePicker.hour, timePicker.minute)
                }

                timePicker.show(parentFragmentManager, timePicker.toString())
            }

            // 種類EditTextがクリックされたとき、種類選択ボトムシートを表示する。
            typeEditText.setOnSafeClickListener {
                val bottomSheet = TypeSelectBottomSheet
                    .newInstance(_viewModel.householdAccountBook.incomeOrExpense, REQUEST_KEY_TYPE_SELECT)
                bottomSheet.show(parentFragmentManager, bottomSheet.toString())
            }

            TooltipCompat.setTooltipText(
                prevDayButton, getString(R.string.date_to_previous))
            TooltipCompat.setTooltipText(
                nextDayButton, getString(R.string.date_to_next))
        }

        _viewModel.householdAccountBookLiveData.observe(viewLifecycleOwner) {
            Logger.d(it)
            refreshIncomeOrExpenseToggleButton()
        }

        lifecycleScope.launchWhenStarted {
            _viewModel.typeNotSelectedEvent.collect { householdAccountBook ->
                // スナックバーを作成する。
                val snackbar = Snackbar.make(
                    readOnlyBinding.mainLayout,
                    getString(R.string.please_select_type),
                    Snackbar.LENGTH_SHORT
                )

                // スナックバーにアクションを設定する。
                snackbar.setAction(R.string.select) {
                    snackbar.dismiss()
                    val bottomSheet = TypeSelectBottomSheet.newInstance(
                        householdAccountBook.incomeOrExpense,
                        REQUEST_KEY_TYPE_SELECT
                    )
                    bottomSheet.show(parentFragmentManager, bottomSheet.toString())
                }

                // スナックバーを表示する。
                snackbar.show()
            }
        }

        lifecycleScope.launchWhenStarted {
            _viewModel.writeCompleteEvent.collect { householdAccountBook ->
                val amountOfMoneyText = "¥ %,d".format(
                    householdAccountBook.amountOfMoney
                )

                // スナックバーを作成する。
                // typeはnullチェック済みなので、強制アンラップする。
                val snackbar = Snackbar.make(
                    readOnlyBinding.mainLayout,
                    getString(
                        R.string.has_written_income_or_expense,
                        getString(householdAccountBook.incomeOrExpense.strResId),
                        getString(householdAccountBook.type!!.strResId),
                        amountOfMoneyText
                    ),
                    Snackbar.LENGTH_SHORT
                )

                // スナックバーにアクションを設定する。
                snackbar.setAction(R.string.ok) {
                    snackbar.dismiss()
                }

                // スナックバーを表示する。
                snackbar.show()
            }
        }

        // 種類選択ボトムシートから選択された種類を取得する。
        setFragmentResultListener(REQUEST_KEY_TYPE_SELECT) { _, bundle ->
            val selectedType =
                bundle.getSerializable(TypeSelectBottomSheet.EXTRA_ENUM_SELECTED_TYPE)

            Logger.d("selectedType: $selectedType")

            // Serializable?からIncomeOrExpenseTypeにキャストする。
            if (selectedType !is IncomeOrExpenseType) {
                return@setFragmentResultListener
            }

            _viewModel.setIncomeOrExpenseType(selectedType)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        nullableBinding = null
    }

    /**
     * 収支トグルボタンの表示を更新する。
     * @exception IllegalStateException [EditViewModel.householdAccountBook]が初期化されていない場合に投げられる。
     */
    private fun refreshIncomeOrExpenseToggleButton() {
        val expenseButtonColor: Int
        val incomeButtonColor: Int

        when (_viewModel.householdAccountBook.incomeOrExpense) {
            IncomeOrExpense.INCOME -> {
                expenseButtonColor = R.color.translucent_red
                incomeButtonColor = R.color.blue
            }
            IncomeOrExpense.EXPENSE -> {
                expenseButtonColor = R.color.red
                incomeButtonColor = R.color.translucent_blue
            }
        }

        readOnlyBinding.apply {
            expenseButton.setBackgroundColor(
                App.getColor(expenseButtonColor)
            )
            incomeButton.setBackgroundColor(
                App.getColor(incomeButtonColor)
            )
        }
    }
}
