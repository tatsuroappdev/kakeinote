package com.tatsuro.app.kakeinote

import android.app.Application
import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary2.FlipperLeakListener
import com.facebook.flipper.plugins.leakcanary2.LeakCanary2FlipperPlugin
import com.facebook.soloader.SoLoader
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tatsuro.app.kakeinote.constant.ErrorMessages.APPLICATION_INSTANCE_NOT_GOTTEN
import leakcanary.LeakCanary

@Suppress("unused")
class App : Application() {

    init {
        instance = this
    }

    companion object {

        /** Appインスタンス */
        private var instance: App? = null

        /** アプリケーションコンテキスト */
        val applicationContext: Context get() =
            instance?.applicationContext ?: error(APPLICATION_INSTANCE_NOT_GOTTEN)
    }

    override fun onCreate() {
        super.onCreate()
        initLogger()
        initFlipper()
    }

    /**
     * orhanobutのLoggerを初期化する。
     *
     * - tagはKakeiNoteとする。
     * - DEBUGのときにのみログ出力する。
     */
    private fun initLogger() {
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

    /**
     * Flipperを初期化する。
     *
     * 有効にするプラグインは以下とする。
     * - InspectorFlipperPlugin
     * - LeakCanary2FlipperPlugin
     */
    private fun initFlipper() {
        SoLoader.init(this,false)

        if (!BuildConfig.DEBUG || !FlipperUtils.shouldEnableFlipper(this)) {
            return
        }

        val client = AndroidFlipperClient.getInstance(this).apply {
            //////////////////////
            // pluginを追加する。
            //////////////////////
            // レイアウト
            val descriptorMapping = DescriptorMapping.withDefaults()
            addPlugin(InspectorFlipperPlugin(this@App, descriptorMapping))

            // データベース
            addPlugin(DatabasesFlipperPlugin(this@App))

            // LeakCanary
            LeakCanary.config = LeakCanary.config.copy(
                onHeapAnalyzedListener = FlipperLeakListener()
            )
            addPlugin(LeakCanary2FlipperPlugin())
        }
        client.start()
    }
}
