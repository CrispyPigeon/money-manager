package com.ds.money_manager.usecases

import com.ds.money_manager.base.helpers.asyncR
import com.ds.money_manager.data.repository.handler.MoneyManagerDataHandler
import kotlinx.coroutines.CoroutineScope
import java.math.BigDecimal
import javax.inject.Inject

class SaveCostUseCase @Inject constructor(
    private val moneyManagerDataHandler: MoneyManagerDataHandler
) {
    internal operator fun invoke(
        scope: CoroutineScope,
        name: String,
        sum: BigDecimal,
        date: String,
        walletId: Int,
        costTypeId: Int
    ) = scope.asyncR {
        moneyManagerDataHandler.postCost(
            name, sum, date, walletId, costTypeId
        )
    }
}