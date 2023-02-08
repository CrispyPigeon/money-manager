package com.ds.money_manager.feature.main.fragment

import androidx.lifecycle.MutableLiveData
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.data.model.EmptyWallet
import com.ds.money_manager.data.model.WalletItem
import com.ds.money_manager.data.model.api.StatisticItemResponse
import com.ds.money_manager.data.model.api.TransactionResponse
import com.ds.money_manager.usecases.GetLastTransactionsUseCase
import com.ds.money_manager.usecases.GetTotalBalanceUseCase
import com.ds.money_manager.usecases.GetTotalStatisticDataUseCase
import com.ds.money_manager.usecases.GetWalletsDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val getWalletDetailsUseCase: GetWalletsDetailsUseCase,
    val getTotalStatisticDataUseCase: GetTotalStatisticDataUseCase,
    val getLastTransactionsUseCase: GetLastTransactionsUseCase,
    val getTotalBalanceUseCase: GetTotalBalanceUseCase
) : DialogsSupportViewModel() {

    val totalBalance = MutableLiveData<BigDecimal>()
    val wallets = MutableLiveData<List<WalletItem>>()
    val transactions = MutableLiveData<List<TransactionResponse>>()
    val totalStatisticData = MutableLiveData<List<StatisticItemResponse>>()

    suspend fun getWalletsDetails() {
        getWalletDetailsUseCase().awaitFoldApi(
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

    suspend fun getStatisticData() {
        val initial = LocalDate.now()
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

    suspend fun getLastTransactions() {
        getLastTransactionsUseCase(5).awaitFoldApi(
            {
                transactions.value = it
            },
            {
                showError(it.title, it.description)
            },
            {
                showError("", it.message!!)
            }
        )
    }

    suspend fun getTotalBalance() {
        getTotalBalanceUseCase().awaitFoldApi(
            {
                totalBalance.value = it
            },
            {
                showError(it.title, it.description)
            },
            {
                showError("", it.message!!)
            }
        )
    }

    fun getAllData() {
        launchUI {
            changeLoadingState(true)
            delay(1000)
            getTotalBalance()
            getWalletsDetails()
            getStatisticData()
            getLastTransactions()
            changeLoadingState(false)
        }
    }
}