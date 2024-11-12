package com.ds.money_manager.data.model.api

import com.google.gson.annotations.SerializedName

class WalletUpdateResponse(
    @SerializedName("walletId") var walletId: Int,
    @SerializedName("name") var name: String
)