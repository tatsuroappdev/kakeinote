package com.tatsuro.app.kakeinote.ui.householdaccountbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tatsuro.app.kakeinote.App
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.constant.IncomeOrExpense
import com.tatsuro.app.kakeinote.database.HouseholdAccountBook
import com.tatsuro.app.kakeinote.databinding.HouseholdAccountBookItemBinding

class HouseholdAccountBookListAdapter(
    private val householdAccountBookList: List<HouseholdAccountBook>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<HouseholdAccountBookListAdapter.ViewHolder>() {

    fun interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

    class ViewHolder(
        private val binding: HouseholdAccountBookItemBinding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(householdAccountBook: HouseholdAccountBook) {
            binding.apply {
                // 種類のアイコン
                householdAccountBook.type?.let {
                    iconImageView.setImageResource(it.drawableResId)
                }

                // 内容
                contentTextView.text = when {
                    householdAccountBook.content != "" -> {
                        householdAccountBook.content
                    }
                    householdAccountBook.type != null -> {
                        App.applicationContext.getText(householdAccountBook.type!!.strResId)
                    }
                    else -> {
                        ""
                    }
                }

                // 金額
                val amountOfMoneyText = "¥ %,d".format(householdAccountBook.amountOfMoney)

                val textColor: Int = when(householdAccountBook.incomeOrExpense) {
                    IncomeOrExpense.INCOME -> {
                        App.getColor(R.color.blue)
                    }
                    IncomeOrExpense.EXPENSE -> {
                        App.getColor(R.color.red)
                    }
                }

                amountOfMoneyTextView.setTextColor(textColor)
                amountOfMoneyTextView.text = amountOfMoneyText

                // クリックリスナ
                root.setOnClickListener {
                    listener.onItemClick(householdAccountBook.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HouseholdAccountBookItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(householdAccountBookList[position])
    }

    override fun getItemCount() = householdAccountBookList.size
}
