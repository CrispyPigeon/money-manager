package com.ds.money_manager.usecases

import com.ds.money_manager.base.helpers.asyncR
import com.ds.money_manager.data.repository.handler.MoneyManagerDataHandler
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDate
import javax.inject.Inject

class GetTotalStatisticDataUseCase @Inject constructor(
    private val moneyManagerDataHandler: MoneyManagerDataHandler
) {
    internal operator fun invoke(
        scope: CoroutineScope,
        dateFrom: LocalDate,
        dateTo: LocalDate,
        walletId: Int? = null
    ) = scope.asyncR {
        moneyManagerDataHandler.getTotalStatisticByDate(dateFrom, dateTo, walletId)
    }
}