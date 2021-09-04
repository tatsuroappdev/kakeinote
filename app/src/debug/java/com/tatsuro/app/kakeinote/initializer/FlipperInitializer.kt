package com.tatsuro.app.kakeinote.initializer

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary2.FlipperLeakListener
import com.facebook.flipper.plugins.leakcanary2.LeakCanary2FlipperPlugin
import com.facebook.soloader.SoLoader
import leakcanary.LeakCanary

object FlipperInitializer {

    /**
     * Flipperを初期化する。
     *
     * 有効にするプラグインは以下とする。
     * - InspectorFlipperPlugin
     * - DatabasesFlipperPlugin
     * - LeakCanary2FlipperPlugin
     *
     * @param context コンテキスト
     */
    fun initialize(context: Context) {
        SoLoader.init(context,false)

        if (!FlipperUtils.shouldEnableFlipper(context)) {
            return
        }

        val client = AndroidFlipperClient.getInstance(context).apply {
            //////////////////////
            // pluginを追加する。
            //////////////////////
            // レイアウト
            val descriptorMapping = DescriptorMapping.withDefaults()
            addPlugin(InspectorFlipperPlugin(context, descriptorMapping))

            // データベース
            addPlugin(DatabasesFlipperPlugin(context))

            // LeakCanary
            LeakCanary.config = LeakCanary.config.copy(
                onHeapAnalyzedListener = FlipperLeakListener()
            )
            addPlugin(LeakCanary2FlipperPlugin())
        }
        client.start()
    }
}
