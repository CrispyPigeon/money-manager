package com.ds.money_manager.usecases

import com.ds.money_manager.data.repository.preferences.AppSharedPreferences
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val preferences: AppSharedPreferences
) {
    internal operator fun invoke() {
        preferences.logOut()
    }
}