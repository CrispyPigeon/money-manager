package com.ds.money_manager.usecases

import com.ds.money_manager.data.repository.preferences.AppSharedPreferences
import javax.inject.Inject

class GetAuthDataUseCase @Inject constructor(
    private val preferences: AppSharedPreferences
) {
    internal operator fun invoke(): Pair<String, String> {
        return Pair(preferences.username, preferences.password)
    }
}