package com.ds.money_manager.usecases

import com.ds.money_manager.base.helpers.asyncR
import com.ds.money_manager.data.repository.handler.MoneyManagerDataHandler
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val moneyManagerDataHandler: MoneyManagerDataHandler
) {
    internal operator fun invoke(
        scope: CoroutineScope,
        dateFrom: String,
        dateTo: String,
        walletId: Int?,
        offset: Int = 0,
        limit: Int = 20
    ) = scope.asyncR {
        moneyManagerDataHandler.getAllTransactionsByDate(dateFrom, dateTo, walletId, offset, limit)
    }
}