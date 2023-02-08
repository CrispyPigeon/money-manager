package com.ds.money_manager.data.model.api

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class TotalBalanceResponse(
    @SerializedName("totalBalance") val totalBalance: BigDecimal
)