package com.ds.money_manager.data.repository.handler

import com.ds.money_manager.data.model.api.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigDecimal
import java.time.LocalDate

interface MoneyManagerDataHandler {
    fun signIn(name: String, password: String): SignInResponse
    fun signUp(name: String, password: String): SignUpResponse
    fun getTotalBalance(): BigDecimal
    fun getAllWalletsDetails(): List<WalletDetailsResponse>
    fun getTotalStatisticByDate(dateFrom: LocalDate, dateTo: LocalDate, walletId: Int? = null): List<StatisticItemResponse>
    fun getAllTransactionsByDate(
        dateFrom: String, dateTo: String, walletId: Int? = null, offset: Int = 0, limit: Int = 20
    ): List<TransactionResponse>
    fun getLastTransactions(limit: Int): List<TransactionResponse>
    fun postWallet(name: String, currencyId: Int): WalletResponse
    fun putWallet(walletId: Int, name: String): WalletUpdateResponse
    fun deleteWallet(walletId: Int)
    fun getWallet(walletId: Int): WalletResponse
    fun postIncome(walletId: Int, name: String, sum: BigDecimal, date: String): IncomeResponse
    fun putIncome(
        incomeId: Int,
        walletId: Int,
        name: String,
        sum: BigDecimal,
        date: String
    ): IncomeResponse
    fun deleteIncome(incomeId: Int)
    fun getCostTypes(): List<CostTypeResponse>
    fun postCost(
        name: String,
        sum: BigDecimal,
        date: String,
        walledId: Int,
        costTypeId: Int
    ): CostResponse
    fun putCost(
        costId: Int,
        name: String,
        sum: BigDecimal,
        date: String,
        walletId: Int,
        costTypeId: Int
    ): CostResponse
    fun deleteCost(costId: Int)
    fun getCost(costId: Int): CostResponse
    fun getIncome(incomeId: Int): IncomeResponse
}