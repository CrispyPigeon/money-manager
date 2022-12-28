package com.ds.money_manager.usecases

import com.ds.money_manager.base.helpers.asyncR
import com.ds.money_manager.data.repository.handler.MoneyManagerDataHandler
import javax.inject.Inject

class SignUpRequestUseCase @Inject constructor(
    private val moneyManagerDataHandler: MoneyManagerDataHandler
) {
    internal operator fun invoke(name: String, password: String) = asyncR {
        moneyManagerDataHandler.signUp(name, password)
    }
}