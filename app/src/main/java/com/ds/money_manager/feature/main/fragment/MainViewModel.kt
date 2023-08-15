package com.ds.money_manager.feature.main.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ds.money_manager.Constants
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.data.model.EmptyWallet
import com.ds.money_manager.data.model.WalletItem
import com.ds.money_manager.data.model.api.StatisticItemResponse
import com.ds.money_manager.data.model.api.TransactionResponse
import com.ds.money_manager.usecases.*
import com.ds.money_manager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val getWalletDetailsUseCase: GetWalletsDetailsUseCase,
    val getTotalStatisticDataUseCase: GetTotalStatisticDataUseCase,
    val getLastTransactionsUseCase: GetLastTransactionsUseCase,
    val getTotalBalanceUseCase: GetTotalBalanceUseCase,
    val logOutUseCase: LogOutUseCase
) : DialogsSupportViewModel() {

    val totalBalance = MutableLiveData<BigDecimal>()
    val totalExpenses = MutableLiveData<BigDecimal>()
    val wallets = MutableLiveData<List<WalletItem>>()
    val transactions = MutableLiveData<List<TransactionResponse>>()
    val totalStatisticData = MutableLiveData<List<StatisticItemResponse>>()
    val monthName = MutableLiveData<String>()
    val logOutSuccessEvent = SingleLiveEvent<Any>()

    private suspend fun getWalletsDetails() {
        getWalletDetailsUseCase(viewModelScope).awaitFoldApi(
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

    private suspend fun getStatisticData() {
        val initial = LocalDate.now()
        val start = initial.withDayOfMonth(1)
        val end = initial.withDayOfMonth(initial.month.length(initial.isLeapYear))

        monthName.value = start.month.getDisplayName(TextStyle.FULL, Locale.getDefault())

        getTotalStatisticDataUseCase(viewModelScope, start, end).awaitFoldApi(
            {
                totalStatisticData.value = it
                totalExpenses.value = it.sumOf { it.amount }
            },
            {
                showError(it.title, it.description)
            },
            {
                showError("", it.message!!)
            }
        )
    }

    private suspend fun getLastTransactions() {
        getLastTransactionsUseCase(viewModelScope, 5).awaitFoldApi(
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

    private suspend fun getTotalBalance() {
        getTotalBalanceUseCase(viewModelScope).awaitFoldApi(
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
            delay(Constants.DEFAULT_DELAY)
            getTotalBalance()
            getWalletsDetails()
            getStatisticData()
            getLastTransactions()
            changeLoadingState(false)
        }
    }

    fun logOut() {
        logOutUseCase()
        logOutSuccessEvent.call()
    }
}