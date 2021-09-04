package com.tatsuro.app.kakeinote.initializer

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tatsuro.app.kakeinote.BuildConfig

object LoggerInitializer {

    /**
     * orhanobutのLoggerを初期化する。
     *
     * - tagはKakeiNoteとする。
     * - DEBUGのときにのみログ出力する。
     */
    fun initialize() {
        // tagを変更する。
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .tag("KakeiNote")
            .build()
        // DEBUGのときにのみログ出力する。
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}
