package com.ds.money_manager.data.repository.api

import com.ds.money_manager.data.model.api.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface MoneyManagerApi {

    @Headers("No-Authentication: true")
    @POST("account/authorize")
    fun signIn(@Body data: SignInRequest): Call<SignInResponse>

    @Headers("No-Authentication: true")
    @POST("account/register")
    fun signUp(@Body data: SignUpRequest): Call<SignUpResponse>

    @GET("wallet/all/details")
    fun getAllWalletsDetails(): Call<List<WalletDetailsResponse>>
}