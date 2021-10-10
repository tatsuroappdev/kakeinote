package com.tatsuro.app.kakeinote.ui.householdaccountbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tatsuro.app.kakeinote.App
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.constant.IncomeOrExpense
import com.tatsuro.app.kakeinote.database.HouseholdAccountBook
import com.tatsuro.app.kakeinote.databinding.HouseholdAccountBookItemBinding
import com.tatsuro.app.kakeinote.ui.setOnSafeClickListener

/**
 * 家計簿リストのアダプタ
 * @property householdAccountBookList 家計簿リスト
 * @property listener 家計簿リスト項目のクリックリスナ
 */
class HouseholdAccountBookListAdapter(
    private val householdAccountBookList: List<HouseholdAccountBook>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<HouseholdAccountBookListAdapter.ViewHolder>() {

    /** 家計簿リスト項目がクリックされたときに呼び出されるコールバックのためのインターフェース定義 */
    fun interface OnItemClickListener {

        /**
         * 家計簿リスト項目がクリックされたときに呼び出される。
         * @param id クリックされた家計簿リスト項目のID
         */
        fun onItemClick(id: Int)
    }

    /**
     * 家計簿リスト項目のビューホルダ
     * @property binding 家計簿リスト項目のビューバインディング
     */
    inner class ViewHolder(
        private val binding: HouseholdAccountBookItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * ビューバインド
         *
         * 家計簿リスト項目の各ビューと[householdAccountBookList]のバインド対象をバインドする。
         * @param position バインド対象の位置
         */
        fun bind(position: Int) {
            binding.apply {
                val householdAccountBook = householdAccountBookList[position]

                // 種類のアイコン
                iconImageView.setImageResource(householdAccountBook.type.drawableResId)

                // 内容
                contentTextView.text = when {
                    householdAccountBook.content != "" -> {
                        householdAccountBook.content
                    }
                    else -> {
                        App.applicationContext.getText(householdAccountBook.type.strResId)
                    }
                }

                // 金額
                val amountOfMoneyText = "¥ %,d".format(householdAccountBook.amountOfMoney)

                val textColor: Int = when (householdAccountBook.incomeOrExpense) {
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
                root.setOnSafeClickListener {
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
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    /**
     * 家計簿リストの項目数を返す。
     * @return 家計簿リストの項目数
     */
    override fun getItemCount() = householdAccountBookList.size
}
