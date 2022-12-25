package com.ds.money_manager.base.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.ds.money_manager.base.presentation.BaseViewState
import com.ds.money_manager.utils.SingleLiveEvent
import com.ds.money_manager.utils.toLiveData
import kotlin.properties.Delegates

abstract class BaseViewModel <ViewState : BaseViewState>(
    initialState: ViewState
) : ViewModel() {

    private val stateMutableLiveData = SingleLiveEvent<ViewState>()
    val stateLiveData = stateMutableLiveData.toLiveData()

    protected var state by Delegates.observable(initialState) { _, _, new ->
        stateMutableLiveData.value = new
    }
}