package com.tatsuro.app.kakeinote.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        // BottomNavigationViewにてフラグメントを切り替えると、切り替えの度にフラグメントが再生成される。
        // よって、ViewPager2にフラグメント切り替えを管理させる。
        binding.apply {
            viewPager2.apply {
                adapter = BottomNavigationViewAdapter(this@MainActivity)
                isUserInputEnabled = false
            }

            // BottomNavigationViewのアイコンが選択されたとき、ViewPager2のフラグメントを切り替える。
            bottomNavigationView.setOnItemSelectedListener { item ->
                val currentItem = when(item.itemId) {
                    R.id.household_account_book_fragment ->
                        BottomNavigationViewAdapter.HOUSEHOLD_ACCOUNT_BOOK_FRAGMENT_ID
                    R.id.search_fragment ->
                        BottomNavigationViewAdapter.SEARCH_FRAGMENT_ID
                    R.id.other_fragment ->
                        BottomNavigationViewAdapter.OTHER_FRAGMENT_ID
                    else -> error(ErrorMessages.INVALID_ITEM_SELECTED)
                }

                viewPager2.setCurrentItem(currentItem, false)

                // 選択されたことをBottomNavigationViewの表示に反映するため、trueを返す。
                return@setOnItemSelectedListener true
            }
        }
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
