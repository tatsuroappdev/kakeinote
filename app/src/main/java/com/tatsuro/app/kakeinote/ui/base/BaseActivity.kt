package com.tatsuro.app.kakeinote.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.strictmode.FragmentStrictMode
import com.tatsuro.app.kakeinote.BuildConfig

/** 基底アクティビティ */
abstract class BaseActivity : AppCompatActivity() {

    /** FragmentManagerにFragmentStrictModeを設定する。 */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!BuildConfig.DEBUG) {
            return
        }

        val fragmentPolicy = FragmentStrictMode.Policy.Builder()
            .detectFragmentReuse()
            .detectFragmentTagUsage()
            .detectRetainInstanceUsage()
            .detectSetUserVisibleHint()
            .detectTargetFragmentUsage()
            .detectWrongFragmentContainer()
            .penaltyLog()
            .build()
        supportFragmentManager.strictModePolicy = fragmentPolicy
    }
}
