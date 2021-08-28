package com.tatsuro.app.kakeinote.ui.details

import androidx.databinding.InverseMethod

object Converters {

    @JvmStatic
    @InverseMethod("stringToInt")
    fun intToString(value: Int?) = value?.toString() ?: ""

    @JvmStatic
    fun stringToInt(value: String) = value.toIntOrNull()
}
