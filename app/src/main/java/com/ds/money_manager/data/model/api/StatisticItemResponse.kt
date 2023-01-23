package com.ds.money_manager.data.model.api

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class StatisticItemResponse(
    @SerializedName("name") var name: String,
    @SerializedName("amount") var amount: BigDecimal,
    @SerializedName("percent") var percent: String,
    @SerializedName("image") var image: String,
    @SerializedName("color") var color: String
)
