package com.tatsuro.app.kakeinote.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.constant.ErrorMessages
import com.tatsuro.app.kakeinote.databinding.MainActivityBinding
import com.tatsuro.app.kakeinote.ui.details.DetailsActivity
import com.tatsuro.app.kakeinote.ui.householdaccountbook.HouseholdAccountBookFragment

/** メインアクティビティ */
class MainActivity : AppCompatActivity(),
    HouseholdAccountBookFragment.OnWriteButtonClickListener,
    HouseholdAccountBookFragment.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

        if (navHostFragment !is NavHostFragment) {
            error(ErrorMessages.FAILED_TO_CAST_NAV_HOST_FRAGMENT)
        }

        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)
    }

    /** 書き込むボタンのクリックイベント */
    override fun onWriteButtonClick() {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }

    /** 家計簿リスト項目のクリックイベント */
    override fun onItemClick(id: Int) {
        TODO("Not yet implemented")
    }
}
