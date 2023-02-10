package com.ds.money_manager.feature.signin

import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.usecases.SaveAuthDataUseCase
import com.ds.money_manager.usecases.SignInRequestUseCase
import com.ds.money_manager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val signInRequestUseCase: SignInRequestUseCase,
    val saveAuthDataUseCase: SaveAuthDataUseCase
) : DialogsSupportViewModel() {

    val signInSuccessEvent = SingleLiveEvent<Any>()

    fun signIn(name: String, password: String) {
        launchUI {
            changeLoadingState(true)
            delay(1000)
            signInRequestUseCase(name, password).awaitFoldApi(
                {
                    saveAuthDataUseCase(name, password, it.token)
                    signInSuccessEvent.call()
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