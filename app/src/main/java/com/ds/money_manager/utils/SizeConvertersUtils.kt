package com.ds.money_manager.utils

import android.content.Context

object SizeConvertersUtils {
    fun toPx(context: Context, dpSize: Int): Int {
        return (dpSize * context.resources.displayMetrics.density).toInt()
    }
}