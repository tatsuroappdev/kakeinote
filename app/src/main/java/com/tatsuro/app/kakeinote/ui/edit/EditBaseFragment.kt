package com.tatsuro.app.kakeinote.ui.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.ui.write.WriteFooterFragment

/** 編集ベースフラグメント */
class EditBaseFragment : Fragment(R.layout.edit_base_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.bodyContainer, EditBodyFragment())
                .replace(R.id.footerContainer, WriteFooterFragment())
                .commitNow()
        }
    }
}
