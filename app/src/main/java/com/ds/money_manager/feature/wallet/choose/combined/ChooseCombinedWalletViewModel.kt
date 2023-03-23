package com.ds.money_manager.feature.wallet.choose.combined

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ds.money_manager.Constants
import com.ds.money_manager.Constants.ALL_WALLETS_VALUE
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.data.model.api.WalletDetailsResponse
import com.ds.money_manager.usecases.GetWalletsDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class ChooseCombinedWalletViewModel @Inject constructor(
    val getWalletDetailsUseCase: GetWalletsDetailsUseCase
) : DialogsSupportViewModel() {

    val wallets = MutableLiveData<List<WalletDetailsResponse>>()

    fun getWalletsDetails(allWalletsStr: String) {
        launchUI {
            changeLoadingState(true)
            delay(Constants.DEFAULT_DELAY)
            getWalletDetailsUseCase(viewModelScope).awaitFoldApi(
                {
                    val walletsDetails = mutableListOf<WalletDetailsResponse>()
                    walletsDetails.add(
                        WalletDetailsResponse(
                            ALL_WALLETS_VALUE,
                            allWalletsStr,
                            BigDecimal.ZERO,
                            BigDecimal.ZERO,
                            it.sumOf { it.balance },
                            it.firstOrNull()?.currency ?: ""
                        )
                    )
                    walletsDetails.addAll(it)
                    wallets.value = walletsDetails
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