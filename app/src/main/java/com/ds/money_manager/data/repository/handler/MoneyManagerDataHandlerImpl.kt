package com.ds.money_manager.data.repository.handler

import com.ds.money_manager.data.model.api.*
import com.ds.money_manager.data.repository.api.MoneyManagerApi
import com.ds.money_manager.utils.ApiErrorUtils
import com.ds.money_manager.utils.ApiException
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MoneyManagerDataHandlerImpl @Inject constructor(val moneyManagerApi: MoneyManagerApi) :
    MoneyManagerDataHandler {

    private val UNKNOWN_ERROR = "Unknown error"

    override fun signIn(name: String, password: String): SignInResponse {
        val result = moneyManagerApi.signIn(SignInRequest(name, password)).execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun signUp(name: String, password: String): SignUpResponse {
        val result = moneyManagerApi.signUp(SignUpRequest(name, password)).execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun checkToken(): Boolean {
        val result = moneyManagerApi.checkToken().execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun getTotalBalance(): BigDecimal {
        val result = moneyManagerApi.getTotalBalance().execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!.totalBalance
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun getAllWalletsDetails(): List<WalletDetailsResponse> {
        val result = moneyManagerApi.getAllWalletsDetails().execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun getTotalStatisticByDate(
        dateFrom: LocalDate,
        dateTo: LocalDate
    ): List<StatisticItemResponse> {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val result = moneyManagerApi.getTotalStatisticByDate(
            dateFrom.format(dateTimeFormatter),
            dateTo.format(dateTimeFormatter)
        ).execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun getAllTransactionsByDate(
        dateFrom: String,
        dateTo: String,
        walletId: Int?,
        offset: Int,
        limit: Int
    ): List<TransactionResponse> {
        val result =
            moneyManagerApi.getAllTransactionsByDate(dateFrom, dateTo, walletId, offset, limit).execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun getLastTransactions(limit: Int): List<TransactionResponse> {
        val result = moneyManagerApi.getLastTransactions(limit).execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun postWallet(name: String, currencyId: Int): WalletResponse {
        val result = moneyManagerApi.postWallet(WalletRequest(name, currencyId)).execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun putWallet(walletId: Int, name: String): WalletUpdateResponse {
        val result = moneyManagerApi.putWallet(WalletUpdateRequest(walletId, name)).execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun deleteWallet(walletId: Int) {
        val result = moneyManagerApi.deleteWallet(walletId).execute()
        if (result.isSuccessful)
            return
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun getWallet(walletId: Int): WalletResponse {
        val result = moneyManagerApi.getWallet(walletId).execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun postIncome(
        walletId: Int,
        name: String,
        sum: BigDecimal,
        date: String
    ): IncomeResponse {
        val result = moneyManagerApi.postIncome(IncomeRequest(walletId, name, sum, date)).execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun putIncome(
        incomeId: Int,
        walletId: Int,
        name: String,
        sum: BigDecimal,
        date: String
    ): IncomeResponse {
        val result =
            moneyManagerApi.putIncome(IncomeUpdateRequest(incomeId, walletId, name, sum, date))
                .execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun deleteIncome(incomeId: Int) {
        val result = moneyManagerApi.deleteIncome(incomeId).execute()
        if (result.isSuccessful)
            return
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun getCostTypes(): List<CostTypeResponse> {
        val result = moneyManagerApi.getCostTypes().execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun postCost(
        name: String,
        sum: BigDecimal,
        date: String,
        walletId: Int,
        costTypeId: Int
    ): CostResponse {
        val result =
            moneyManagerApi.postCost(CostRequest(name, sum, date, walletId, costTypeId)).execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun putCost(
        costId: Int,
        name: String,
        sum: BigDecimal,
        date: String,
        walledId: Int,
        costTypeId: Int
    ): CostResponse {
        val result =
            moneyManagerApi.putCost(
                CostUpdateRequest(
                    costId,
                    name,
                    sum,
                    date,
                    walledId,
                    costTypeId
                )
            )
                .execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun deleteCost(costId: Int) {
        val result = moneyManagerApi.deleteCost(costId).execute()
        if (result.isSuccessful)
            return
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun getCost(costId: Int): CostResponse {
        val result = moneyManagerApi.getCost(costId).execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun getIncome(incomeId: Int): IncomeResponse {
        val result = moneyManagerApi.getIncome(incomeId).execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description, result.code()) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }
}