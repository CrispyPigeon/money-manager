package com.ds.money_manager.usecases

import com.ds.money_manager.base.helpers.asyncR
import com.ds.money_manager.data.repository.handler.MoneyManagerDataHandler
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class SignInRequestUseCase @Inject constructor(
    private val moneyManagerDataHandler: MoneyManagerDataHandler
) {
    internal operator fun invoke(scope: CoroutineScope, name: String, password: String) = scope.asyncR {
        moneyManagerDataHandler.signIn(name, password)
    }
}