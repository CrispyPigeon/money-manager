package com.ds.money_manager.utils

import com.ds.money_manager.Constants
import java.math.BigDecimal
import java.math.RoundingMode

object CurrencyUtils {
    //TODO update to work with currency from APU
    fun showAmount(amount: BigDecimal)
    = "${amount.setScale(Constants.CURRENCY_SCALE, RoundingMode.UP)} zl"
}