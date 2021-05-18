package com.tatsuro.app.kakeinote.ui.householdaccountbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.writeButton.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            startActivity(intent)
        }
    }
}
