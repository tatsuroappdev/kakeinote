package com.tatsuro.app.kakeinote.ui.typeselect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tatsuro.app.kakeinote.constant.IncomeOrExpenseType
import com.tatsuro.app.kakeinote.databinding.TypeItemBinding

/**
 * 種類のアダプタ
 *
 * [TypeSelectBottomSheet]にて選択対象の収支種類を[RecyclerView]に紐付ける。
 * @property types 選択対象の収支種類
 * @property listener 種類選択ボタンのクリックリスナ
 */
class TypeAdapter(
    private val types: Array<IncomeOrExpenseType>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<TypeAdapter.ViewHolder>() {

    /** 種類選択ボタンがクリックされたときに呼び出されるコールバックのためのインターフェース定義 */
    fun interface OnItemClickListener {

        /**
         * 種類選択ボタンがクリックされたときに呼び出される。
         * @param type クリックされた収支種類
         */
        fun onItemClick(type: IncomeOrExpenseType)
    }

    /**
     * 種類選択ボタンのビューホルダ
     * @property binding 種類項目のビューバインディング
     * @property listener 種類選択ボタンのクリックリスナ
     */
    class ViewHolder(
        private val binding: TypeItemBinding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * ビューバインド
         *
         * 種類選択ボタンと引数[type]をバインドする。
         * @param type 収支の種類
         */
        fun bind(type: IncomeOrExpenseType) {
            binding.textButton.apply {
                setIconResource(type.drawableResId)
                setText(type.strResId)
                setOnClickListener {
                    listener.onItemClick(type)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TypeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(types[position])
    }

    /**
     * 選択対象の収支種類の数を返す。
     * @return 選択対象の収支種類の数
     */
    override fun getItemCount() = types.size
}
