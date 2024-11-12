package com.ds.money_manager.data.repository.preferences

interface AppSharedPreferences {
    var token: String
    var username: String
    var password: String
    var isAuthorized: Boolean

    fun signIn(token: String, username: String, password: String)
    fun logOut()
}