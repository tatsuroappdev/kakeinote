package com.tatsuro.app.kakeinote.ui.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.TooltipCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.orhanobut.logger.Logger
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.constant.ErrorMessages
import com.tatsuro.app.kakeinote.constant.IncomeOrExpense
import com.tatsuro.app.kakeinote.constant.IncomeOrExpenseType
import com.tatsuro.app.kakeinote.databinding.DetailsFragmentBinding
import com.tatsuro.app.kakeinote.ui.setOnSafeClickListener
import com.tatsuro.app.kakeinote.ui.typeselect.TypeSelectBottomSheet
import kotlinx.coroutines.runBlocking

/** 詳細フラグメント */
class DetailsFragment : Fragment(R.layout.details_fragment) {

    companion object {

        /** 種類選択ボトムシートから選択された種類を取得するためのリクエストキー */
        private const val REQUEST_KEY_TYPE_SELECT = "typeSelect"

        /**
         * フラグメントのインスタントを返す。
         * @return フラグメントインスタント
         */
        fun newInstance() = DetailsFragment()
    }

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding ?: error(ErrorMessages.DATA_BINDING_NOT_BOUND)
    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DetailsFragmentBinding.bind(view).also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }

        viewModel.householdAccountBook.observe(viewLifecycleOwner) {
            Logger.d(it)
            refreshIncomeOrExpenseToggleButton()
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

            viewModel.setIncomeOrExpenseType(selectedType)
        }

        binding.apply {
            val viewModel = viewModel ?: error(ErrorMessages.VIEW_MODEL_NOT_INITIALIZED)
            val householdAccountBook = viewModel
                .householdAccountBook
                .value ?: error(ErrorMessages.HOUSEHOLD_ACCOUNT_BOOK_NOT_INITIALIZED)

            // 日付EditTextがクリックされたとき、日付変更ダイアログを表示する。
            dateEditText.setOnSafeClickListener {
                val datePicker = MaterialDatePicker.Builder
                    .datePicker()
                    .setSelection(viewModel.dateAtEpochMilli)
                    .build().apply {
                        addOnPositiveButtonClickListener {
                            viewModel.dateAtEpochMilli = it
                        }
                    }

                datePicker.show(parentFragmentManager, datePicker.toString())
            }

            // 時間EditTextがクリックされたとき、時間変更ダイアログを表示する。
            timeEditText.setOnSafeClickListener {
                val timePicker = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setTitleText(R.string.please_select_time)
                    .setHour(householdAccountBook.time.hour)
                    .setMinute(householdAccountBook.time.minute)
                    .build()

                timePicker.addOnPositiveButtonClickListener {
                    viewModel.setTime(timePicker.hour, timePicker.minute)
                }

                timePicker.show(parentFragmentManager, timePicker.toString())
            }

            // 種類EditTextがクリックされたとき、種類選択ボトムシートを表示する。
            typeEditText.setOnSafeClickListener {
                val bottomSheet = TypeSelectBottomSheet
                    .newInstance(householdAccountBook.incomeOrExpense, REQUEST_KEY_TYPE_SELECT)
                bottomSheet.show(parentFragmentManager, bottomSheet.toString())
            }

            onceWriteButton.setOnClickListener {
                if (householdAccountBook.type == null) {
                    // スナックバーを表示する。
                    val snackbar = Snackbar.make(
                        binding.mainLayout,
                        getString(R.string.please_select_type),
                        Snackbar.LENGTH_SHORT)
                    snackbar.setAction(R.string.select) {
                        snackbar.dismiss()
                        val bottomSheet = TypeSelectBottomSheet.newInstance(
                            householdAccountBook.incomeOrExpense, REQUEST_KEY_TYPE_SELECT)
                        bottomSheet.show(parentFragmentManager, bottomSheet.toString())
                    }
                    snackbar.show()

                    return@setOnClickListener
                }

                // ボタンを無効化する。
                it.isClickable = false
                it.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.translucent_light_blue))

                // データベースに保存する。
                runBlocking {
                    viewModel.upsert()
                }

                requireActivity().finish()
            }

            repeatWriteButton.setOnClickListener {
                if (householdAccountBook.type == null) {
                    // スナックバーを表示する。
                    val snackbar = Snackbar.make(
                        binding.mainLayout,
                        getString(R.string.please_select_type),
                        Snackbar.LENGTH_SHORT)
                    snackbar.setAction(R.string.select) {
                        snackbar.dismiss()
                        val bottomSheet = TypeSelectBottomSheet.newInstance(
                            householdAccountBook.incomeOrExpense, REQUEST_KEY_TYPE_SELECT)
                        bottomSheet.show(parentFragmentManager, bottomSheet.toString())
                    }
                    snackbar.show()

                    return@setOnClickListener
                }

                // ボタンを無効化する。
                it.isClickable = false
                it.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.translucent_light_blue))

                // データベースに保存する。
                runBlocking {
                    viewModel.upsert()
                }

                // 家計簿を初期化する。
                viewModel.initHouseholdAccountBook()

                // 書き込み結果をSnackbarに表示する。
                // typeはnullチェック済みなので、強制アンラップする。
                val snackbar = Snackbar.make(
                    binding.mainLayout,
                    getString(
                        R.string.write_income_or_expense,
                        getString(householdAccountBook.incomeOrExpense.strResId),
                        getString(householdAccountBook.type!!.strResId),
                        householdAccountBook.amountOfMoney),
                    Snackbar.LENGTH_SHORT)
                snackbar.setAction(R.string.ok) {
                    snackbar.dismiss()
                }
                snackbar.show()

                // ボタンを有効化する。
                it.isClickable = true
                it.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.light_blue))
            }

            deleteButton.setOnClickListener {
                // ボタンを無効化する。
                it.isClickable = false
                it.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.translucent_red))

                runBlocking {
                    viewModel.delete()
                }

                requireActivity().finish()
            }

            TooltipCompat.setTooltipText(
                prevDayButton, getString(R.string.date_to_previous))
            TooltipCompat.setTooltipText(
                nextDayButton, getString(R.string.date_to_next))
            TooltipCompat.setTooltipText(
                onceWriteButton, getString(R.string.write_new_and_close_activity))
            TooltipCompat.setTooltipText(
                repeatWriteButton, getString(R.string.write_new_and_can_write_other))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 収支トグルボタンの表示を更新する。
     * @exception IllegalStateException viewModelのhouseholdAccountBookが初期化されていない場合に投げられる。
     */
    private fun refreshIncomeOrExpenseToggleButton() {
        val householdAccountBook = viewModel
            .householdAccountBook
            .value ?: error(ErrorMessages.HOUSEHOLD_ACCOUNT_BOOK_NOT_INITIALIZED)

        val expenseButtonColor: Int
        val incomeButtonColor: Int

        when(householdAccountBook.incomeOrExpense) {
            IncomeOrExpense.INCOME -> {
                expenseButtonColor = R.color.translucent_red
                incomeButtonColor = R.color.blue
            }
            IncomeOrExpense.EXPENSE -> {
                expenseButtonColor = R.color.red
                incomeButtonColor = R.color.translucent_blue
            }
        }

        binding.apply {
            expenseButton.setBackgroundColor(
                ContextCompat.getColor(requireContext(), expenseButtonColor))
            incomeButton.setBackgroundColor(
                ContextCompat.getColor(requireContext(), incomeButtonColor))
        }
    }
}
