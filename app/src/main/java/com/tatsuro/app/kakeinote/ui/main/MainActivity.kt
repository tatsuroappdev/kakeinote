package com.tatsuro.app.kakeinote.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tatsuro.app.kakeinote.R

/** 家計簿Activity. */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}