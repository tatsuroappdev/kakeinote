package com.tatsuro.app.kakeinote.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.databinding.MainActivityBinding
import com.tatsuro.app.kakeinote.ui.details.DetailsActivity
import com.tatsuro.app.kakeinote.ui.householdaccountbook.HouseholdAccountBookFragment

class MainActivity : AppCompatActivity(),
    HouseholdAccountBookFragment.OnWriteButtonClickListener,
    HouseholdAccountBookFragment.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavigation, navController)
    }

    override fun onWriteButtonClick() {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }

    override fun onItemClick(id: Int) {
        TODO("Not yet implemented")
    }
}
