package com.ds.money_manager.usecases

import com.ds.money_manager.base.helpers.asyncR
import com.ds.money_manager.data.repository.handler.MoneyManagerDataHandler
import kotlinx.coroutines.CoroutineScope
import java.math.BigDecimal
import javax.inject.Inject

class UpdateCostUseCase @Inject constructor(
    private val moneyManagerDataHandler: MoneyManagerDataHandler
) {
    internal operator fun invoke(
        scope: CoroutineScope,
        costId: Int,
        name: String,
        sum: BigDecimal,
        date: String,
        walledId: Int,
        costTypeId: Int
    ) = scope.asyncR {
        moneyManagerDataHandler.putCost(costId, name, sum, date, walledId, costTypeId)
    }
}