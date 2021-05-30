package com.tatsuro.app.kakeinote.ui

import android.os.Handler
import android.os.Looper
import android.view.View

private const val CLICKABLE_DELAY_TIME = 100L

fun <T: View> T.setOnSafeClickListener(listener: (it: T) -> Unit) {
    setOnClickListener { view ->
        if (view == null) {
            return@setOnClickListener
        }
        view.isEnabled = false

        Handler(Looper.getMainLooper()).postDelayed(
            { view.isEnabled = true },
            CLICKABLE_DELAY_TIME
        )

        listener.invoke(view as T)
    }
}
