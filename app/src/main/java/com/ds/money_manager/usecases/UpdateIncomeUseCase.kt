package com.ds.money_manager.usecases

import com.ds.money_manager.base.helpers.asyncR
import com.ds.money_manager.data.repository.handler.MoneyManagerDataHandler
import kotlinx.coroutines.CoroutineScope
import java.math.BigDecimal
import javax.inject.Inject

class UpdateIncomeUseCase @Inject constructor(
    private val moneyManagerDataHandler: MoneyManagerDataHandler
) {
    internal operator fun invoke(
        scope: CoroutineScope,
        incomeId: Int,
        walletId: Int,
        name: String,
        sum: BigDecimal,
        date: String
    ) = scope.asyncR {
        moneyManagerDataHandler.putIncome(incomeId, walletId, name, sum, date)
    }
}