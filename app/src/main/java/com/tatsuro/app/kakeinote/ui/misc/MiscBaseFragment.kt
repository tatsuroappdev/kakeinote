package com.tatsuro.app.kakeinote.ui.misc

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentContainerView
import com.tatsuro.app.kakeinote.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * その他ベースフラグメント
 *
 * [PreferenceFragmentCompat]を継承した[MiscFragment]に[Toolbar]を表示するため、
 * [Toolbar]と[FragmentContainerView]を持つ[MiscBaseFragment]を作成する。
 */
@AndroidEntryPoint
class MiscBaseFragment : Fragment(R.layout.misc_base_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, MiscFragment())
                .commitNow()
        }
    }
}
