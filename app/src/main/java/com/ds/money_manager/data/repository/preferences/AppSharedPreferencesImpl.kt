package com.ds.money_manager.data.repository.preferences

import android.content.Context
import android.content.SharedPreferences
import com.ds.money_manager.R

class AppSharedPreferencesImpl(context: Context) : AppSharedPreferences {
    private val TOKEN_KEY = "TOKEN_KEY"
    private val USERNAME_KEY = "USERNAME_KEY"
    private val PASSWORD_KEY = "PASSWORD_KEY"

    private val preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(
            context.getString(R.string.shared_preferences_key),
            Context.MODE_PRIVATE
        )
    }

    override var token: String
        get() = preferences.getString(TOKEN_KEY, "")!!
        set(value) = with(preferences.edit()) {
            putString(TOKEN_KEY, value)
            apply()
        }

    override var username: String
        get() = preferences.getString(USERNAME_KEY, "")!!
        set(value) = with(preferences.edit()) {
            putString(USERNAME_KEY, value)
            apply()
        }

    override var password: String
        get() = preferences.getString(PASSWORD_KEY, "")!!
        set(value) = with(preferences.edit()) {
            putString(PASSWORD_KEY, value)
            apply()
        }

    override fun signIn(token: String, username: String, password: String) {
        this.token = token
        this.username = username
        this.password = password
    }

    override fun logOut() {
        this.token = ""
        this.username = ""
        this.password = ""
    }
}
