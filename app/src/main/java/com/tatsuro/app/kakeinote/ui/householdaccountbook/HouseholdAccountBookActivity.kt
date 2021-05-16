package com.tatsuro.app.kakeinote.ui.householdaccountbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.databinding.HouseholdAccountBookActivityBinding

/** 家計簿Activity. */
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

        binding.writeButton.setOnClickListener {
            // TODO:詳細Activityへの遷移を実装する。
        }
    }
}