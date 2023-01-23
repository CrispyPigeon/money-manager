package com.ds.money_manager.feature.main.fragment

import androidx.lifecycle.MutableLiveData
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsViewModel
import com.ds.money_manager.data.model.EmptyWallet
import com.ds.money_manager.data.model.WalletItem
import com.ds.money_manager.data.model.api.StatisticItemResponse
import com.ds.money_manager.usecases.GetTotalStatisticDataUseCase
import com.ds.money_manager.usecases.GetWalletsDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val signInRequestUseCase: GetWalletsDetailsUseCase,
    val getTotalStatisticDataUseCase: GetTotalStatisticDataUseCase
) : DialogsViewModel() {

    val wallets = MutableLiveData<List<WalletItem>>()
    val totalStatisticData = MutableLiveData<List<StatisticItemResponse>>()

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

    fun getStatisticData() {
        launchUI {
            val initial = LocalDate.of(2022, 11, 1)
            val start = initial.withDayOfMonth(1)
            val end = initial.withDayOfMonth(initial.month.length(initial.isLeapYear))
            getTotalStatisticDataUseCase(start, end).awaitFoldApi(
                {
                    totalStatisticData.value = it
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