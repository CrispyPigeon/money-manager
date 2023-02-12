package com.ds.money_manager.data.repository.api

import com.ds.money_manager.data.model.api.*
import retrofit2.Call
import retrofit2.http.*

interface MoneyManagerApi {

    @Headers("No-Authentication: true")
    @POST("account/authorize")
    fun signIn(@Body data: SignInRequest): Call<SignInResponse>

    @Headers("No-Authentication: true")
    @POST("account/register")
    fun signUp(@Body data: SignUpRequest): Call<SignUpResponse>

    @GET("account/validate_token")
    fun checkToken(): Call<Boolean>

    @GET("wallet")
    fun getWallet(@Query("walletId") walletId: Int): Call<WalletResponse>

    @POST("wallet/add")
    fun postWallet(@Body data: WalletRequest): Call<WalletResponse>

    @PUT("wallet/update")
    fun putWallet(@Body data: WalletUpdateRequest): Call<WalletUpdateResponse>

    @DELETE("wallet/delete")
    fun deleteWallet(@Query("walletId") walletId: Int): Call<Void>

    @GET("wallet/all/details")
    fun getAllWalletsDetails(): Call<List<WalletDetailsResponse>>

    @GET("statistic/balance")
    fun getTotalBalance(
    ): Call<TotalBalanceResponse>

    @GET("statistic/items")
    fun getTotalStatisticByDate(
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String
    ): Call<List<StatisticItemResponse>>

    @GET("statistic/transactions/all")
    fun getAllTransactionsByDate(
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20
    ): Call<List<TransactionResponse>>

    @GET("statistic/transactions/last")
    fun getLastTransactions(
        @Query("limit") limit: Int = 20
    ): Call<List<TransactionResponse>>
}