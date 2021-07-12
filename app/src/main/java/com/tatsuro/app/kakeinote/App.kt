package com.tatsuro.app.kakeinote

import android.app.Application
import android.content.Context
import android.os.StrictMode
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.strictmode.FragmentStrictMode
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
        nullableInstance = this
    }

    companion object {

        /** nullを許容するAppインスタンス */
        private var nullableInstance: App? = null

        /** nullを許容しないAppインスタンス */
        private val nonNullInstance get() =
            nullableInstance ?: error(APPLICATION_INSTANCE_NOT_GOTTEN)

        /** アプリケーションコンテキスト */
        val applicationContext: Context get() = nonNullInstance.applicationContext

        /**
         * 引数[id]に紐付く色を返す。
         * @param id 色のリソースID
         * @return 引数[id]に紐付く色
         * @exception IllegalStateException アプリケーションインスタンスが取得される前に呼ばれた場合に投げられる。
         * @exception android.content.res.Resources.NotFoundException 引数[id]が存在しない場合に投げられる。
         */
        fun getColor(@ColorRes id: Int) = ContextCompat.getColor(nonNullInstance, id)
    }

    override fun onCreate() {
        super.onCreate()
        initStrictMode()
        initLogger()
        initFlipper()
    }

    /** StrictModeを初期化する。 */
    private fun initStrictMode() {
        if (!BuildConfig.DEBUG) {
            return
        }

        // ネットワークにはアクセスしないため、ネットワーク操作の検出を行わない。
        val threadPolicy = StrictMode.ThreadPolicy.Builder()
            .detectDiskReads()
            .detectDiskWrites()
            .penaltyLog()
            .build()
        StrictMode.setThreadPolicy(threadPolicy)

        val vmPolicy = StrictMode.VmPolicy.Builder()
            .detectActivityLeaks()
            .detectLeakedClosableObjects()
            .detectLeakedSqlLiteObjects()
            .penaltyLog()
            .build()
        StrictMode.setVmPolicy(vmPolicy)

        val fragmentPolicy = FragmentStrictMode.Policy.Builder()
            .detectFragmentReuse()
            .detectFragmentTagUsage()
            .detectRetainInstanceUsage()
            .detectSetUserVisibleHint()
            .detectTargetFragmentUsage()
            .detectWrongFragmentContainer()
            .penaltyLog()
            .build()
        FragmentStrictMode.setDefaultPolicy(fragmentPolicy)
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
