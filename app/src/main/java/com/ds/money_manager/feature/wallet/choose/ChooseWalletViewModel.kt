package com.ds.money_manager.feature.wallet.choose

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.data.model.api.WalletDetailsResponse
import com.ds.money_manager.usecases.GetWalletsDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class ChooseWalletViewModel @Inject constructor(
    val getWalletDetailsUseCase: GetWalletsDetailsUseCase
) : DialogsSupportViewModel() {

    val wallets = MutableLiveData<List<WalletDetailsResponse>>()

    fun getWalletsDetails() {
        launchUI {
            changeLoadingState(true)
            delay(1000)
            getWalletDetailsUseCase(viewModelScope).awaitFoldApi(
                {
                    wallets.value = it
                },
                {
                    showError(it.title, it.description)
                },
                {
                    showError("", it.message!!)
                }
            )

            changeLoadingState(false)
        }
    }
}