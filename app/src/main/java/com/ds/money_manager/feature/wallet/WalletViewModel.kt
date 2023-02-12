package com.ds.money_manager.feature.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.data.model.api.WalletResponse
import com.ds.money_manager.usecases.GetWalletUseCase
import com.ds.money_manager.usecases.RemoveWalletUseCase
import com.ds.money_manager.usecases.SaveWalletUseCase
import com.ds.money_manager.usecases.UpdateWalletUseCase
import com.ds.money_manager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    val getWalletUseCase: GetWalletUseCase,
    val removeWalletUseCase: RemoveWalletUseCase,
    val saveWalletUseCase: SaveWalletUseCase,
    val updateWalletUseCase: UpdateWalletUseCase
) : DialogsSupportViewModel() {

    val successEvent = SingleLiveEvent<Any>()
    val wallet = MutableLiveData<WalletResponse>()

    fun getWalletData(walletId: Int) {
        launchUI {
            getWalletUseCase(viewModelScope,walletId).awaitFoldApi(
                {
                    wallet.value = it
                },
                {
                    showError(it.title, it.description)
                },
                {
                    showError("", it.message.toString())
                }
            )
        }
    }

    fun saveWallet(name: String) {
        launchUI {
            saveWalletUseCase(viewModelScope,name).awaitFoldApi(
                {
                    successEvent.call()
                },
                {
                    showError(it.title, it.description)
                },
                {
                    showError("", it.message.toString())
                }
            )
        }
    }

    fun updateWallet(walletId: Int, name: String) {
        launchUI {
            updateWalletUseCase(viewModelScope,walletId, name).awaitFoldApi(
                {
                    successEvent.call()
                },
                {
                    showError(it.title, it.description)
                },
                {
                    showError("", it.message.toString())
                }
            )
        }
    }

    fun removeWallet(walletId: Int) {
        launchUI {
            removeWalletUseCase(viewModelScope,walletId).awaitFoldApi(
                {
                    successEvent.call()
                },
                {
                    showError(it.title, it.description)
                },
                {
                    showError("", it.message.toString())
                }
            )
        }
    }
}