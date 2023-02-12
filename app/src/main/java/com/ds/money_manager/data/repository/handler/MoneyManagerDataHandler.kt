package com.ds.money_manager.data.repository.handler

import com.ds.money_manager.data.model.api.*
import retrofit2.Call
import retrofit2.http.Query
import java.math.BigDecimal
import java.time.LocalDate

interface MoneyManagerDataHandler {
    fun signIn(name: String, password: String): SignInResponse
    fun signUp(name: String, password: String): SignUpResponse
    fun checkToken(): Boolean
    fun getTotalBalance(): BigDecimal
    fun getAllWalletsDetails(): List<WalletDetailsResponse>
    fun getTotalStatisticByDate(dateFrom: LocalDate, dateTo: LocalDate): List<StatisticItemResponse>
    fun getLastTransactions(limit: Int): List<TransactionResponse>
    fun postWallet(name: String, currencyId: Int): WalletResponse
    fun putWallet(walletId: Int, name: String): WalletUpdateResponse
    fun deleteWallet(walletId: Int)
    fun getWallet(walletId: Int): WalletResponse
}