package com.ds.money_manager.data.repository.handler

import com.ds.money_manager.data.model.api.*
import com.ds.money_manager.data.repository.api.MoneyManagerApi
import com.ds.money_manager.utils.ApiErrorUtils
import com.ds.money_manager.utils.ApiException
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
            apiError?.let { throw ApiException(it.title, it.description) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun signUp(name: String, password: String): SignUpResponse {
        val result = moneyManagerApi.signUp(SignUpRequest(name, password)).execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }

    override fun getAllWalletsDetails(): List<WalletDetailsResponse> {
        val result = moneyManagerApi.getAllWalletsDetails().execute()
        if (result.isSuccessful && result.body() != null)
            return result.body()!!
        else {
            val apiError = ApiErrorUtils.parseError(result.errorBody()!!)
            apiError?.let { throw ApiException(it.title, it.description) }
                ?: throw Exception(UNKNOWN_ERROR)
        }
    }
}