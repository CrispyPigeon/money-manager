package com.ds.money_manager.feature.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.money_manager.base.helpers.awaitFold
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.usecases.*
import com.ds.money_manager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    val signInRequestUseCase: SignInRequestUseCase,
    val isAuthorizedUseCase: IsAuthorizedUseCase,
    val getAuthDataUseCase: GetAuthDataUseCase,
    val checkTokenUseCase: CheckTokenUseCase,
    val saveAuthDataUseCase: SaveAuthDataUseCase
) : ViewModel() {
    private val UNAUTHORIZED = 401

    val signInSuccessEvent = SingleLiveEvent<Any>()
    val signInErrorEvent = SingleLiveEvent<Any>()

    fun processAuth() {
        launchUI {
            delay(1500)

            if (!isAuthorizedUseCase())
                signInErrorEvent.call()

            checkTokenUseCase(viewModelScope).awaitFoldApi(
                {
                    signInSuccessEvent.call()
                },
                {
                    if (it.code == UNAUTHORIZED)
                        auth()
                    else
                        signInErrorEvent.call()
                },
                {
                    signInErrorEvent.call()
                }
            )
        }
    }

    private suspend fun auth() {
        val signInData = getAuthDataUseCase()
        signInRequestUseCase(viewModelScope, signInData.first, signInData.second).awaitFold(
            {
                saveAuthDataUseCase(signInData.first, signInData.second, it.token)
                signInSuccessEvent.call()
            },
            {
                signInErrorEvent.call()
            }
        )
    }
}