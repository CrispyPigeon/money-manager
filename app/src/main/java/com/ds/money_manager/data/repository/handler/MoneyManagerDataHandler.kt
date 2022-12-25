package com.ds.money_manager.data.repository.handler

import com.ds.money_manager.data.model.api.SignInResponse

interface MoneyManagerDataHandler {
    fun signIn(name: String, password: String) : SignInResponse
}