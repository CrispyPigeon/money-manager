package com.ds.money_manager.data.repository.handler

import com.ds.money_manager.data.model.api.SignInResponse
import com.ds.money_manager.data.model.api.SignUpResponse
import com.ds.money_manager.data.model.api.WalletDetailsResponse
import retrofit2.Call

interface MoneyManagerDataHandler {
    fun signIn(name: String, password: String) : SignInResponse
    fun signUp(name: String, password: String) : SignUpResponse
    fun getAllWalletsDetails(): List<WalletDetailsResponse>
}