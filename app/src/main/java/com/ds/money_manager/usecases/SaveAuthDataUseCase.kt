package com.ds.money_manager.usecases

import com.ds.money_manager.data.repository.preferences.AppSharedPreferences
import javax.inject.Inject

class SaveAuthDataUseCase @Inject constructor(
    private val preferences: AppSharedPreferences
) {
    internal operator fun invoke(username: String, password: String, token: String) {
        preferences.signIn(token, username, password)
    }
}