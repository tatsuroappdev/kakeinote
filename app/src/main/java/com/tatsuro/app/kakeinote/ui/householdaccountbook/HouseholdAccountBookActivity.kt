package com.tatsuro.app.kakeinote.ui.householdaccountbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.TooltipCompat
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.databinding.HouseholdAccountBookActivityBinding
import com.tatsuro.app.kakeinote.ui.details.DetailsActivity

/** 家計簿アクティビティ */
class HouseholdAccountBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = HouseholdAccountBookActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HouseholdAccountBookFragment.newInstance())
                .commitNow()
        }

        binding.apply {
            searchButton.setOnClickListener {
                // TODO:検索画面を表示する処理を実装する。
//                val intent = Intent(
//                    this@HouseholdAccountBookActivity, SearchActivity::class.java)
//                startActivity(intent)
            }
            writeButton.setOnClickListener {
                val intent = Intent(
                    this@HouseholdAccountBookActivity, DetailsActivity::class.java)
                startActivity(intent)
            }
            TooltipCompat.setTooltipText(
                searchButton, getString(R.string.show_search_activity))
            TooltipCompat.setTooltipText(
                writeButton, getString(R.string.show_new_write_activity))
        }
    }
}
