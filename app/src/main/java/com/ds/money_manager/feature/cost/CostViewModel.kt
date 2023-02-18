package com.ds.money_manager.feature.cost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.extensions.toApiString
import com.ds.money_manager.usecases.SaveCostUseCase
import com.ds.money_manager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CostViewModel @Inject constructor(
    val saveCostUseCase: SaveCostUseCase
) : DialogsSupportViewModel() {

    val successEvent = SingleLiveEvent<Any>()
    val localeDate = MutableLiveData<LocalDate>()

    fun saveCost(
        name: String,
        sum: BigDecimal,
        walledId: Int,
        costTypeId: Int
    ) {
        launchUI {
            changeLoadingState(true)
            delay(1000)
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
}