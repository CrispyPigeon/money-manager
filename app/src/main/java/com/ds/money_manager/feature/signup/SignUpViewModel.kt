package com.ds.money_manager.feature.signup

import androidx.lifecycle.viewModelScope
import com.ds.money_manager.Constants
import com.ds.money_manager.R
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.usecases.SaveAuthDataUseCase
import com.ds.money_manager.usecases.SignUpRequestUseCase
import com.ds.money_manager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val signUpRequestUseCase: SignUpRequestUseCase,
    val saveAuthDataUseCase: SaveAuthDataUseCase
) : DialogsSupportViewModel() {

    val authSuccessEvent = SingleLiveEvent<Any>()

    fun validatePasswords(password: String, rPassword: String): Boolean {
        if (password != rPassword)
            showErrorWithIds(
                R.string.dialog_error_title,
                R.string.dialog_error_passwords_description
            )

        return password == rPassword
    }

    fun signUp(name: String, password: String, rPassword: String) {
        launchUI {
            if (!validatePasswords(password, rPassword))
                return@launchUI

            changeLoadingState(true)
            delay(Constants.DEFAULT_DELAY)
            signUpRequestUseCase(viewModelScope,name, password).awaitFoldApi(
                {
                    saveAuthDataUseCase(name, password, it.token)
                    authSuccessEvent.call()
                },
                {
                    showError(it.title,it.description)
                },
                {
                    showError("", it.message.toString())
                }
            )
            changeLoadingState(false)
        }
    }
}