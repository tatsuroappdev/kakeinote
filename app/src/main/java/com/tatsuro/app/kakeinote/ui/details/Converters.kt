package com.tatsuro.app.kakeinote.ui.details

import androidx.databinding.InverseMethod

object Converters {

    @JvmStatic
    @InverseMethod("stringToInt")
    fun intToString(value: Int): String = value.toString()

    @JvmStatic
    fun stringToInt(value: String): Int = value.toInt()
}
