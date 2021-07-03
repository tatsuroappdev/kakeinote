package com.tatsuro.app.kakeinote.ui.householdaccountbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.tatsuro.app.kakeinote.App
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.databinding.HouseholdAccountBookHeaderBinding
import java.time.DayOfWeek
import java.time.LocalDate

class HouseholdAccountBookHeaderAdapter(
    private val date: LocalDate,
    private val incomeAmount: Int,
    private val expenseAmount: Int
) :  RecyclerView.Adapter<HouseholdAccountBookHeaderAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: HouseholdAccountBookHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(date: LocalDate, incomeAmount: Int, expenseAmount: Int) {
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
        holder.bind(date, incomeAmount, expenseAmount)
    }

    override fun getItemCount() = 1
}