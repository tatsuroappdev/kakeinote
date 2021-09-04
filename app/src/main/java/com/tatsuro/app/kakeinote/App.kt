package com.tatsuro.app.kakeinote

import android.app.Application
import android.content.Context
import android.content.res.Resources.NotFoundException
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.tatsuro.app.kakeinote.initializer.FlipperInitializer
import com.tatsuro.app.kakeinote.initializer.LoggerInitializer
import com.tatsuro.app.kakeinote.initializer.StrictModeInitializer

@Suppress("unused")
class App : Application() {

    init {
        instance = this
    }

    companion object {

        /** Appインスタンス */
        private lateinit var instance: App

        /** アプリケーションコンテキスト */
        val applicationContext: Context get() = instance.applicationContext

        /**
         * 引数[id]に紐付く色を返す。
         * @param id 色のリソースID
         * @return 引数[id]に紐付く色
         * @exception NotFoundException 引数[id]が存在しない場合に投げられる。
         */
        fun getColor(@ColorRes id: Int) = ContextCompat.getColor(instance, id)
    }

    override fun onCreate() {
        super.onCreate()
        StrictModeInitializer.initialize()
        LoggerInitializer.initialize()
        FlipperInitializer.initialize(this)
    }
}
