package com.ds.money_manager.usecases

import com.ds.money_manager.base.helpers.asyncR
import com.ds.money_manager.data.repository.handler.MoneyManagerDataHandler
import java.time.LocalDate
import javax.inject.Inject

class GetTotalStatisticDataUseCase @Inject constructor(
    private val moneyManagerDataHandler: MoneyManagerDataHandler
) {
    internal operator fun invoke(dateFrom: LocalDate, dateTo: LocalDate) = asyncR {
        moneyManagerDataHandler.getTotalStatisticByDate(dateFrom, dateTo)
    }
}