package com.ds.money_manager.data.model.api

import java.math.BigDecimal

data class CostRequest(
    val name: String,
    val sum: BigDecimal,
    val date: String,
    val walletId: Int,
    val costTypeId: Int
)
