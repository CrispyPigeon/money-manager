package com.ds.money_manager.data.model.api

import com.google.gson.annotations.SerializedName

data class WalletResponse(
    @SerializedName("walletId") var walletId: Int,
    @SerializedName("name") var name: String,
    @SerializedName("currencyId") var currencyId: Int
)