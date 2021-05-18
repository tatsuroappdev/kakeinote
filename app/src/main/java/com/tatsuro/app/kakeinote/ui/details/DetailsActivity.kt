package com.tatsuro.app.kakeinote.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.databinding.DetailsActivityBinding

/** 詳細アクティビティ */
class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ツールバーを設定する。
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            // ホームボタンの表示を有効にする。
            setDisplayHomeAsUpEnabled(true)
            // ホームボタンの押下を有効にする。
            setHomeButtonEnabled(true)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailsFragment.newInstance())
                .commitNow()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
