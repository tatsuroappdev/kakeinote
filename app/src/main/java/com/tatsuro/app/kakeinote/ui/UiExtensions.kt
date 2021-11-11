package com.tatsuro.app.kakeinote.ui

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 連打対策済みのクリックリスナ
 *
 * クリックされてから100msまでの間、[View]のクリックを無効にする。
 * @param listener クリックリスナ
 */
fun View.setOnSafeClickListener(listener: View.OnClickListener) {
    this.setOnClickListener { view ->
        view.isClickable = false
        view.postDelayed(
            {
                view.isClickable = true
            },
            100L
        )
        listener.onClick(view)
    }
}

/**
 * フラグメント自身をオーナーとするビューモデルを取得する。
 * @param modelClass 取得したいビューモデルのクラスオブジェクト
 * @return ビューモデル
 */
@MainThread
fun <T : ViewModel> Fragment.getViewModel(modelClass: Class<T>) =
    ViewModelProvider(this).get(modelClass)

/**
 * アクティビティをオーナーとするビューモデルを取得する。
 * @param modelClass 取得したいビューモデルのクラスオブジェクト
 * @return ビューモデル
 */
@MainThread
fun <T : ViewModel> Fragment.getActivityViewModel(modelClass: Class<T>) =
    ViewModelProvider(this.requireActivity()).get(modelClass)

/** カラーステートリストを取得する。 */
fun View.getColorStateList(@ColorRes id: Int) = ContextCompat.getColorStateList(this.context, id)
