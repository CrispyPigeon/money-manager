package com.ds.money_manager.feature.signin

import androidx.lifecycle.ViewModel
import com.ds.money_manager.base.helpers.awaitFold
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.BaseViewState
import com.ds.money_manager.base.presentation.viewmodels.BaseViewModel
import com.ds.money_manager.usecases.SaveSignInDataUseCase
import com.ds.money_manager.usecases.SignInRequestUseCase
import com.ds.money_manager.utils.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val signInRequestUseCase: SignInRequestUseCase,
    val saveSignInDataUseCase: SaveSignInDataUseCase
) : BaseViewModel<SignInViewModel.ViewState>(ViewState.Initial) {

    fun signIn(name: String, password: String) {
        launchUI {
            state = ViewState.IsDataLoading(true)
            delay(2000)
            signInRequestUseCase(name, password).awaitFold(
                {
                    saveSignInDataUseCase(name, password, it.token)
                    state = ViewState.SignInSuccessful
                },
                {
                    state = when (it) {
                        is ApiException -> {
                            ViewState.SignInFailure(it.title, it.description)
                        }
                        else -> {
                            ViewState.SignInFailure("", it.message!!)
                        }
                    }
                }
            )
            state = ViewState.IsDataLoading(false)
        }
    }

    sealed class ViewState : BaseViewState {
        object Initial : ViewState()
        class IsDataLoading(val flag: Boolean) : ViewState()
        class SignInFailure(val title: String, val message: String) : ViewState()
        object SignInSuccessful : ViewState()
    }
}