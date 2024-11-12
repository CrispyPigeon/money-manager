package com.ds.money_manager.feature.cost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ds.money_manager.Constants
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.data.model.api.CostResponse
import com.ds.money_manager.data.model.api.WalletResponse
import com.ds.money_manager.extensions.toApiString
import com.ds.money_manager.extensions.toLocalDateTime
import com.ds.money_manager.usecases.GetCostUseCase
import com.ds.money_manager.usecases.RemoveCostUseCase
import com.ds.money_manager.usecases.SaveCostUseCase
import com.ds.money_manager.usecases.UpdateCostUseCase
import com.ds.money_manager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CostViewModel @Inject constructor(
    val saveCostUseCase: SaveCostUseCase,
    val getCostUseCase: GetCostUseCase,
    val updateCostUseCase: UpdateCostUseCase,
    val removeCostUseCase: RemoveCostUseCase
) : DialogsSupportViewModel() {

    val successEvent = SingleLiveEvent<Any>()
    val localeDate = MutableLiveData<LocalDateTime>()
    val cost = MutableLiveData<CostResponse>()

    fun saveCost(
        name: String,
        sum: BigDecimal,
        walledId: Int,
        costTypeId: Int
    ) {
        launchUI {
            changeLoadingState(true)
            delay(Constants.DEFAULT_DELAY)
            saveCostUseCase(
                viewModelScope,
                name,
                sum,
                localeDate.value!!.toApiString(),
                walledId, costTypeId
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

    fun getCost(costId: Int) {
        launchUI {
            changeLoadingState(true)
            delay(Constants.DEFAULT_DELAY)
            getCostUseCase(
                viewModelScope,
                costId
            ).awaitFoldApi(
                {
                    cost.value = it
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

    fun updateCost(name: String,
                   sum: BigDecimal) {
        launchUI {
            changeLoadingState(true)
            delay(Constants.DEFAULT_DELAY)
            updateCostUseCase(
                viewModelScope,
                cost.value!!.costId,
                name,
                sum,
                localeDate.value!!.toApiString(),
                cost.value!!.walledId,
                cost.value!!.costTypeId
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

    fun removeCost(costId: Int) {
        launchUI {
            changeLoadingState(true)
            delay(Constants.DEFAULT_DELAY)
            removeCostUseCase(
                viewModelScope,
                costId
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