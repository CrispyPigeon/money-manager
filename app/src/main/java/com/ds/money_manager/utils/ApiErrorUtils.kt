package com.ds.money_manager.utils

import com.ds.money_manager.data.model.api.ErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody


object ApiErrorUtils {
    fun parseError(errorBody: ResponseBody): ErrorResponse? {
        return try {
            Gson().fromJson(errorBody.string(), ErrorResponse::class.java)
        } catch (exception: Exception) {
            null
        }
    }
}