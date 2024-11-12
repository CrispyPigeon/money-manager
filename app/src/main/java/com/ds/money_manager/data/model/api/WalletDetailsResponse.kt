package com.ds.money_manager.data.model.api

import com.ds.money_manager.data.model.WalletItem
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class WalletDetailsResponse(
    @SerializedName("walletId") var walletId: Int,
    @SerializedName("name") var name: String,
    @SerializedName("income") var income: BigDecimal,
    @SerializedName("outcome") var outcome: BigDecimal,
    @SerializedName("balance") var balance: BigDecimal,
    @SerializedName("currency") var currency: String
) : WalletItem