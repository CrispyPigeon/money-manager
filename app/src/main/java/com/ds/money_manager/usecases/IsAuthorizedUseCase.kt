package com.ds.money_manager.usecases

import com.ds.money_manager.data.repository.preferences.AppSharedPreferences
import javax.inject.Inject

class IsAuthorizedUseCase @Inject constructor(
    private val preferences: AppSharedPreferences
) {
    internal operator fun invoke(): Boolean {
        return preferences.isAuthorized
    }
}