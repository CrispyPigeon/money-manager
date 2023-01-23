package com.ds.money_manager.feature.main.fragment

import androidx.lifecycle.MutableLiveData
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsViewModel
import com.ds.money_manager.data.model.EmptyWallet
import com.ds.money_manager.data.model.WalletItem
import com.ds.money_manager.data.model.api.WalletDetailsResponse
import com.ds.money_manager.usecases.GetWalletsDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val signInRequestUseCase: GetWalletsDetailsUseCase,
) : DialogsViewModel() {

    val wallets = MutableLiveData<List<WalletItem>>()

    fun getWalletsDetails() {
        launchUI {
            signInRequestUseCase().awaitFoldApi(
                {
                   val list = mutableListOf<WalletItem>()
                    list.addAll(it)
                    list.add(EmptyWallet())
                    wallets.value = list
                },
                {
                    showError(it.title, it.description)
                },
                {
                    showError("", it.message!!)
                }
            )
        }
    }
}