package com.tatsuro.app.kakeinote.ui.edit

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.databinding.EditActivityBinding
import com.tatsuro.app.kakeinote.ui.base.BaseActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/** 編集アクティビティ */
class EditActivity : BaseActivity() {

    private val viewModel: EditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = EditActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ツールバーを設定する。
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            // ホームボタンの表示を有効にする。
            setDisplayHomeAsUpEnabled(true)
            // ホームボタンの押下を有効にする。
            setHomeButtonEnabled(true)
        }

        // ビューモデルのアクティビティ終了イベントを収集したとき、アクティビティを終了する。
        // ビューの更新を行わないため、アクティビティのライフサイクルに関係なく常にイベントを収集する。
        lifecycleScope.launch {
            viewModel.activityFinishEvent.collect {
                finish()
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, EditBaseFragment())
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
