package com.ds.money_manager.data.repository.handler

import com.ds.money_manager.Constants.UNAUTHORIZED_CODE
import com.ds.money_manager.data.model.api.*
import com.ds.money_manager.data.repository.api.MoneyManagerApi
import com.ds.money_manager.data.repository.preferences.AppSharedPreferences
import com.ds.money_manager.utils.ApiErrorUtils
import com.ds.money_manager.utils.ApiException
import okhttp3.Response
import okhttp3.ResponseBody
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MoneyManagerDataHandlerImpl @Inject constructor(
    private val moneyManagerApi: MoneyManagerApi,
    private val preferences: AppSharedPreferences
) : MoneyManagerDataHandler {

    private val UNKNOWN_ERROR = "Unknown error"

    override fun signIn(name: String, password: String): SignInResponse {
        val result = moneyManagerApi.signIn(SignInRequest(name, password)).execute()

        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun signUp(name: String, password: String): SignUpResponse {
        val result = moneyManagerApi.signUp(SignUpRequest(name, password)).execute()

        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun getTotalBalance(): BigDecimal {
        var result = moneyManagerApi.getTotalBalance().execute()
        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result = moneyManagerApi.getTotalBalance().execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!.totalBalance
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun getAllWalletsDetails(): List<WalletDetailsResponse> {
        var result = moneyManagerApi.getAllWalletsDetails().execute()
        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result = moneyManagerApi.getAllWalletsDetails().execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun getTotalStatisticByDate(
        dateFrom: LocalDate,
        dateTo: LocalDate
    ): List<StatisticItemResponse> {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        var result = moneyManagerApi.getTotalStatisticByDate(
            dateFrom.format(dateTimeFormatter),
            dateTo.format(dateTimeFormatter)
        ).execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result = moneyManagerApi.getTotalStatisticByDate(
                dateFrom.format(dateTimeFormatter),
                dateTo.format(dateTimeFormatter)
            ).execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun getAllTransactionsByDate(
        dateFrom: String,
        dateTo: String,
        walletId: Int?,
        offset: Int,
        limit: Int
    ): List<TransactionResponse> {
        var result =
            moneyManagerApi.getAllTransactionsByDate(dateFrom, dateTo, walletId, offset, limit)
                .execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result =
                moneyManagerApi.getAllTransactionsByDate(dateFrom, dateTo, walletId, offset, limit)
                    .execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun getLastTransactions(limit: Int): List<TransactionResponse> {
        var result = moneyManagerApi.getLastTransactions(limit).execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result = moneyManagerApi.getLastTransactions(limit).execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun postWallet(name: String, currencyId: Int): WalletResponse {
        var result = moneyManagerApi.postWallet(WalletRequest(name, currencyId)).execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result = moneyManagerApi.postWallet(WalletRequest(name, currencyId)).execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun putWallet(walletId: Int, name: String): WalletUpdateResponse {
        var result = moneyManagerApi.putWallet(WalletUpdateRequest(walletId, name)).execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result = moneyManagerApi.putWallet(WalletUpdateRequest(walletId, name)).execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun deleteWallet(walletId: Int) {
        var result = moneyManagerApi.deleteWallet(walletId).execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result = moneyManagerApi.deleteWallet(walletId).execute()
        }
        if (result.isSuccessful)
            return
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun getWallet(walletId: Int): WalletResponse {
        var result = moneyManagerApi.getWallet(walletId).execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result = moneyManagerApi.getWallet(walletId).execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun postIncome(
        walletId: Int,
        name: String,
        sum: BigDecimal,
        date: String
    ): IncomeResponse {
        var result = moneyManagerApi.postIncome(IncomeRequest(walletId, name, sum, date)).execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result = moneyManagerApi.postIncome(IncomeRequest(walletId, name, sum, date)).execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun putIncome(
        incomeId: Int,
        walletId: Int,
        name: String,
        sum: BigDecimal,
        date: String
    ): IncomeResponse {
        var result =
            moneyManagerApi.putIncome(IncomeUpdateRequest(incomeId, walletId, name, sum, date))
                .execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result =
                moneyManagerApi.putIncome(IncomeUpdateRequest(incomeId, walletId, name, sum, date))
                    .execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun deleteIncome(incomeId: Int) {
        var result = moneyManagerApi.deleteIncome(incomeId).execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result = moneyManagerApi.deleteIncome(incomeId).execute()
        }
        if (result.isSuccessful)
            return
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun getCostTypes(): List<CostTypeResponse> {
        var result = moneyManagerApi.getCostTypes().execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result = moneyManagerApi.getCostTypes().execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun postCost(
        name: String,
        sum: BigDecimal,
        date: String,
        walletId: Int,
        costTypeId: Int
    ): CostResponse {
        var result =
            moneyManagerApi.postCost(CostRequest(name, sum, date, walletId, costTypeId)).execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result =
                moneyManagerApi.postCost(CostRequest(name, sum, date, walletId, costTypeId))
                    .execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
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
        var result =
            moneyManagerApi.putCost(
                CostUpdateRequest(
                    costId,
                    name,
                    sum,
                    date,
                    walledId,
                    costTypeId
                )
            ).execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result =
                moneyManagerApi.putCost(
                    CostUpdateRequest(
                        costId,
                        name,
                        sum,
                        date,
                        walledId,
                        costTypeId
                    )
                ).execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun deleteCost(costId: Int) {
        var result = moneyManagerApi.deleteCost(costId).execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result = moneyManagerApi.deleteCost(costId).execute()
        }
        if (result.isSuccessful)
            return
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun getCost(costId: Int): CostResponse {
        var result = moneyManagerApi.getCost(costId).execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result = moneyManagerApi.getCost(costId).execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    override fun getIncome(incomeId: Int): IncomeResponse {
        var result = moneyManagerApi.getIncome(incomeId).execute()

        if (result.code() == UNAUTHORIZED_CODE) {
            updateToken()
            result = moneyManagerApi.getIncome(incomeId).execute()
        }
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            throw configureError(result.errorBody()!!, result.code())
        }
    }

    private fun configureError(errorBody: ResponseBody, code: Int): Exception {
        val apiError = ApiErrorUtils.parseError(errorBody)
        return if (apiError != null)
            ApiException(apiError.title, apiError.description, code)
        else
            Exception(UNKNOWN_ERROR)
    }

    private fun updateToken() {
        val response = signIn(preferences.username, preferences.password)
        preferences.token = response.token
    }
}