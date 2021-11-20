package com.tatsuro.app.kakeinote.ui.misc

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.tatsuro.app.kakeinote.R
import dagger.hilt.android.AndroidEntryPoint

/** その他フラグメント */
@AndroidEntryPoint
class MiscFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
