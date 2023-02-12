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
}