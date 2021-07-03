package com.tatsuro.app.kakeinote.ui.householdaccountbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.tatsuro.app.kakeinote.App
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.databinding.HouseholdAccountBookHeaderBinding
import java.time.DayOfWeek
import java.time.LocalDate

/**
 * 家計簿ヘッダのアダプタ
 *
 * 家計簿ヘッダは日毎に表示する。
 * 家計簿ヘッダには、日、曜日、そしてその日の収入額と支出額を表示する。
 * @property date ヘッダに表示する日付
 * @property incomeAmount ヘッダに表示する収入額
 * @property expenseAmount ヘッダに表示する支出額
 */
class HouseholdAccountBookHeaderAdapter(
    private val date: LocalDate,
    private val incomeAmount: Int,
    private val expenseAmount: Int
) :  RecyclerView.Adapter<HouseholdAccountBookHeaderAdapter.ViewHolder>() {

    /**
     * 家計簿ヘッダのビューホルダ
     * @property binding 家計簿ヘッダのビューバインディング
     */
    inner class ViewHolder(
        private val binding: HouseholdAccountBookHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * ビューバインド
         *
         * ヘッダのビューに、日、曜日、そしてその日の収入額と支出額をバインドする。
         */
        fun bind() {
            binding.apply {
                val context = App.applicationContext
                val dayOfWeek = context.resources
                    .getStringArray(R.array.day_of_week)[date.dayOfWeek.ordinal]

                dayTextView.text =
                    context.getString(R.string.formatted_day, date.dayOfMonth)

                val drawableResId = when (date.dayOfWeek) {
                    DayOfWeek.SATURDAY -> R.drawable.bg_saturday
                    DayOfWeek.SUNDAY -> R.drawable.bg_sunday
                    else -> R.drawable.bg_weekdays
                }

                dayOfWeekTextView.apply {
                    text = context.getString(R.string.formatted_day_of_week, dayOfWeek)
                    background = AppCompatResources.getDrawable(context, drawableResId)
                }

                incomeAmountTextView.text = "¥ %,d".format(incomeAmount)
                expenseAmountTextView.text = "¥ %,d".format(expenseAmount)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HouseholdAccountBookHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    /**
     * 家計簿ヘッダは項目が1つしかないので、必ず1を返す。
     * @return 1を返す。
     */
    override fun getItemCount() = 1
}
