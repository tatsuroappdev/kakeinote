package com.tatsuro.app.kakeinote

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary2.FlipperLeakListener
import com.facebook.flipper.plugins.leakcanary2.LeakCanary2FlipperPlugin
import com.facebook.soloader.SoLoader
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import leakcanary.LeakCanary

@Suppress("unused")
class App : Application() {

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
        // DEBUGのときだけ、ログ出力する。
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    /**
     * Flipperを初期化する。
     *
     * 有効にするプラグインは以下の通りとする。
     * - InspectorFlipperPlugin
     * - LeakCanary2FlipperPlugin
     */
    private fun initFlipper() {
        SoLoader.init(this,false)
        if(BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)){
            val client = AndroidFlipperClient.getInstance(this).apply {
                //////////////////////
                // pluginを追加する。
                //////////////////////
                // レイアウト
                val descriptorMapping = DescriptorMapping.withDefaults()
                addPlugin(InspectorFlipperPlugin(this@App, descriptorMapping))

                // LeakCanary
                LeakCanary.config = LeakCanary.config.copy(
                    onHeapAnalyzedListener = FlipperLeakListener()
                )
                addPlugin(LeakCanary2FlipperPlugin())
            }
            client.start()
        }
    }
}