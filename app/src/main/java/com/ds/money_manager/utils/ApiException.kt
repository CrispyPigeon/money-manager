package com.ds.money_manager.utils

import com.ds.money_manager.data.model.api.ErrorResponse

class ApiException(val title: String, val description: String) : Exception(description) {
}