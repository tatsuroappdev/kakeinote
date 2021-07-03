package com.tatsuro.app.kakeinote.ui.householdaccountbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tatsuro.app.kakeinote.App
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.constant.IncomeOrExpense
import com.tatsuro.app.kakeinote.database.HouseholdAccountBook
import com.tatsuro.app.kakeinote.databinding.HouseholdAccountBookItemBinding

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
     * @property listener 家計簿リスト項目のクリックリスナ
     */
    class ViewHolder(
        private val binding: HouseholdAccountBookItemBinding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * ビューバインド
         *
         * 家計簿リスト項目の各ビューと引数[householdAccountBook]をバインドする。
         * @param householdAccountBook 家計簿
         */
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

    /**
     * 家計簿リストの項目数を返す。
     * @return 家計簿リストの項目数
     */
    override fun getItemCount() = householdAccountBookList.size
}
