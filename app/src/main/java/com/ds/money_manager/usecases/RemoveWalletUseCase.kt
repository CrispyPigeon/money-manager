package com.ds.money_manager.usecases

import com.ds.money_manager.base.helpers.asyncR
import com.ds.money_manager.data.repository.handler.MoneyManagerDataHandler
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class RemoveWalletUseCase @Inject constructor(
    private val moneyManagerDataHandler: MoneyManagerDataHandler
) {
    internal operator fun invoke(scope: CoroutineScope, walletId: Int) = scope.asyncR {
        moneyManagerDataHandler.deleteWallet(walletId)
    }
}