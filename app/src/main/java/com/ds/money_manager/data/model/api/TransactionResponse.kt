package com.ds.money_manager.data.model.api

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class TransactionResponse(
    @SerializedName("transactionId") var transactionId: Int,
    @SerializedName("name") var name: String,
    @SerializedName("dateTime") var dateTime: String,
    @SerializedName("amount") var amount: BigDecimal,
    @SerializedName("typeImage") var typeImage: String,
    @SerializedName("transactionType") var transactionType: String
)
