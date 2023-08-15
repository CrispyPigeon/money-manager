package com.ds.money_manager.feature.statistic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.data.model.WalletInfo
import com.ds.money_manager.data.model.api.StatisticItemResponse
import com.ds.money_manager.usecases.GetTotalStatisticDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    val getTotalStatisticDataUseCase: GetTotalStatisticDataUseCase,
) : DialogsSupportViewModel() {

    val walletInfo = MutableLiveData<WalletInfo>()
    val totalExpenses = MutableLiveData<BigDecimal>()
    val totalStatisticData = MutableLiveData<List<StatisticItemResponse>>()
    val monthName = MutableLiveData<String>()

    var dateRange: Pair<LocalDate, LocalDate>? = null

    init {
        val initial = LocalDate.now()
        val start = initial.withDayOfMonth(1)
        val end = initial.withDayOfMonth(initial.month.length(initial.isLeapYear))

        dateRange = Pair(
            start,
            end
        )
    }

    fun getStatisticData() {
        launchUI {
            changeLoadingState(true)

            monthName.value =
                dateRange!!.first.month.getDisplayName(TextStyle.FULL, Locale.getDefault())

            getTotalStatisticDataUseCase(
                viewModelScope,
                dateRange!!.first,
                dateRange!!.second,
                walletInfo.value!!.walletId
            ).awaitFoldApi(
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


            changeLoadingState(false)
        }
    }

    fun previousMonth() {
        val currentData = dateRange!!
        val start = currentData.first.minusMonths(1).withDayOfMonth(1)
        val end = start.withDayOfMonth(start.month.length(start.isLeapYear))
        dateRange = Pair(
            start,
            end
        )
        getStatisticData()
    }

    fun nextMonth() {
        val currentData = dateRange!!
        val start = currentData.first.plusMonths(1).withDayOfMonth(1)
        val end = start.withDayOfMonth(start.month.length(start.isLeapYear))
        dateRange = Pair(
            start,
            end
        )
        getStatisticData()
    }
}