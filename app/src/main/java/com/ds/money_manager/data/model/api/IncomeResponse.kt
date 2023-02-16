package com.ds.money_manager.data.model.api

import java.math.BigDecimal

data class IncomeResponse(
    val incomeId: Int,
    val walletId: Int,
    val name: String,
    val sum: BigDecimal,
    val date: String
)
