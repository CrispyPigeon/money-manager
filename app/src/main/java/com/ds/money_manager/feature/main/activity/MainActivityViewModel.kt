package com.ds.money_manager.feature.main.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ds.money_manager.usecases.IsAuthorizedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val getWalletDetailsUseCase: IsAuthorizedUseCase
) :  ViewModel(){
    val isAuthorized = MutableLiveData<Boolean>()

    fun isAuthorized(){
        isAuthorized.value = getWalletDetailsUseCase()
    }
}