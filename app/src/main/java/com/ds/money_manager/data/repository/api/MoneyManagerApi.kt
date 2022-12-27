package com.ds.money_manager.data.repository.api

import com.ds.money_manager.data.model.api.SignInRequest
import com.ds.money_manager.data.model.api.SignInResponse
import com.ds.money_manager.data.model.api.SignUpRequest
import com.ds.money_manager.data.model.api.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MoneyManagerApi {

    @POST("account/authorize")
    fun signIn(@Body data: SignInRequest): Call<SignInResponse>

    @POST("account/register")
    fun signUp(@Body data: SignUpRequest): Call<SignUpResponse>
}