package com.tatsuro.app.kakeinote.ui.write

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tatsuro.app.kakeinote.R
import com.tatsuro.app.kakeinote.databinding.WriteFooterFragmentBinding
import com.tatsuro.app.kakeinote.ui.edit.EditViewModel
import com.tatsuro.app.kakeinote.ui.getActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

/** 書き込みフッターフラグメント */
@AndroidEntryPoint
class WriteFooterFragment : Fragment(R.layout.write_footer_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WriteFooterFragmentBinding.bind(view).apply {
            viewModel = getActivityViewModel(EditViewModel::class.java)
            lifecycleOwner = viewLifecycleOwner
        }
    }
}
