package com.tatsuro.app.kakeinote.initializer

import android.os.StrictMode
import com.tatsuro.app.kakeinote.BuildConfig

object StrictModeInitializer {

    /** StrictModeを初期化する。 */
    fun initialize() {
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
    }
}
