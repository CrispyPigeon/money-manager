package com.ds.money_manager.data.model.api

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.time.LocalDate

data class TransactionResponse(
    @SerializedName("name") var name: String,
    @SerializedName("dateTime") var dateTime: LocalDate,
    @SerializedName("amount") var amount: BigDecimal,
    @SerializedName("typeImage") var typeImage: String,
    @SerializedName("transactionType") var transactionType: String
)
