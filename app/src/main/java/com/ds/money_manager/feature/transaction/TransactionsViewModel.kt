package com.ds.money_manager.feature.transaction

import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.usecases.GetWalletUseCase
import com.ds.money_manager.usecases.RemoveWalletUseCase
import com.ds.money_manager.usecases.SaveWalletUseCase
import com.ds.money_manager.usecases.UpdateWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel@Inject constructor(
    val getWalletUseCase: GetWalletUseCase,
    val removeWalletUseCase: RemoveWalletUseCase,
    val saveWalletUseCase: SaveWalletUseCase,
    val updateWalletUseCase: UpdateWalletUseCase
) : DialogsSupportViewModel() {
}