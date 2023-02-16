package com.ds.money_manager.feature.income

import androidx.lifecycle.MutableLiveData
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel@Inject constructor(
    val saveIncomeUseCase: SaveIncomeUseCase
) : DialogsSupportViewModel() {

    val totalBalance = MutableLiveData<BigDecimal>()


}