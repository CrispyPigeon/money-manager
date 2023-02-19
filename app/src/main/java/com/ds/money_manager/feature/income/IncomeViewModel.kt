package com.ds.money_manager.feature.income

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.data.model.api.CostResponse
import com.ds.money_manager.data.model.api.IncomeResponse
import com.ds.money_manager.extensions.toApiString
import com.ds.money_manager.extensions.toLocalDateTime
import com.ds.money_manager.usecases.GetIncomeUseCase
import com.ds.money_manager.usecases.RemoveIncomeUseCase
import com.ds.money_manager.usecases.SaveIncomeUseCase
import com.ds.money_manager.usecases.UpdateIncomeUseCase
import com.ds.money_manager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    val saveIncomeUseCase: SaveIncomeUseCase,
    val getIncomeUseCase: GetIncomeUseCase,
    val updateIncomeUseCase: UpdateIncomeUseCase,
    val removeIncomeUseCase: RemoveIncomeUseCase
) : DialogsSupportViewModel() {

    val successEvent = SingleLiveEvent<Any>()
    val localeDate = MutableLiveData<LocalDateTime>()
    val income = MutableLiveData<IncomeResponse>()

    fun saveIncome(walletId: Int, name: String, amount: BigDecimal) {
        launchUI {
            changeLoadingState(true)
            delay(1000)
            saveIncomeUseCase(
                viewModelScope,
                walletId,
                name,
                amount,
                localeDate.value!!.toApiString()
            ).awaitFoldApi(
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
            changeLoadingState(false)
        }
    }

    fun getIncome(incomeId: Int) {
        launchUI {
            changeLoadingState(true)
            delay(1000)
            getIncomeUseCase(
                viewModelScope,
                incomeId
            ).awaitFoldApi(
                {
                    income.value = it
                    localeDate.value = it.date.toLocalDateTime()
                },
                {
                    showError(it.title, it.description)
                },
                {
                    showError("", it.message.toString())
                }
            )
            changeLoadingState(false)
        }
    }

    fun updateIncome(name: String,
                   sum: BigDecimal) {
        launchUI {
            changeLoadingState(true)
            delay(1000)
            updateIncomeUseCase(
                viewModelScope,
                income.value!!.incomeId,
                income.value!!.walletId,
                name,
                sum,
                localeDate.value!!.toApiString()
            ).awaitFoldApi(
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
            changeLoadingState(false)
        }
    }

    fun removeIncome(incomeId: Int) {
        launchUI {
            changeLoadingState(true)
            delay(1000)
            removeIncomeUseCase(
                viewModelScope,
                incomeId
            ).awaitFoldApi(
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
            changeLoadingState(false)
        }
    }
}