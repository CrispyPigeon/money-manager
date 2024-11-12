package com.ds.money_manager.data.model.api

import java.math.BigDecimal

data class CostUpdateRequest (
    val costId: Int,
    val name: String,
    val sum: BigDecimal,
    val date: String,
    val walledId: Int,
    val costTypeId: Int
)