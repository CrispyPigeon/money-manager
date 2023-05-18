package com.ds.money_manager.feature.statistic

import androidx.lifecycle.MutableLiveData
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.data.model.WalletInfo
import com.ds.money_manager.data.model.api.StatisticItemResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
) : DialogsSupportViewModel() {

    val walletInfo =  MutableLiveData<WalletInfo>()
    val totalExpenses = MutableLiveData<BigDecimal>()
    val totalStatisticData = MutableLiveData<List<StatisticItemResponse>>()

    fun getStatisticData(from: LocalDate, to: LocalDate, walletId: Int) {
        launchUI {
            //TODO get Statistic by wallet id method
        }
    }
}