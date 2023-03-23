package com.ds.money_manager.feature.costtype

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ds.money_manager.Constants
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.data.model.api.CostTypeResponse
import com.ds.money_manager.usecases.GetCostTypesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class ChooseCostTypeViewModel @Inject constructor(
    val getCostTypesUseCase: GetCostTypesUseCase
) : DialogsSupportViewModel() {

    val costTypes = MutableLiveData<List<CostTypeResponse>>()

    fun getCostTypes() {
        launchUI {
            changeLoadingState(true)
            delay(Constants.DEFAULT_DELAY)
            getCostTypesUseCase(viewModelScope).awaitFoldApi(
                {
                    costTypes.value = it
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