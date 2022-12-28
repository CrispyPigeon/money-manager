package com.ds.money_manager.feature.signup

import com.ds.money_manager.R
import com.ds.money_manager.base.helpers.awaitFold
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.state.BaseViewState
import com.ds.money_manager.base.presentation.viewmodels.DialogsViewModel
import com.ds.money_manager.usecases.SaveAuthDataUseCase
import com.ds.money_manager.usecases.SignUpRequestUseCase
import com.ds.money_manager.utils.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val signUpRequestUseCase: SignUpRequestUseCase,
    val saveAuthDataUseCase: SaveAuthDataUseCase
) : DialogsViewModel() {

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
            delay(2000)
            signUpRequestUseCase(name, password).awaitFoldApi(
                {
                    saveAuthDataUseCase(name, password, it.token)
                    //state = ViewState.SignUpSuccessful
                },
                {
                    //state = when (it) {
                    //    is ApiException -> {
                    //        ViewState.SignUpFailure(it.title, it.description)
                    //    }
                    //    else -> {
                    //        ViewState.SignUpFailure("", it.message!!)
                    //    }
                    //}
                },
                {

                }
            )
            changeLoadingState(true)
        }
    }
}