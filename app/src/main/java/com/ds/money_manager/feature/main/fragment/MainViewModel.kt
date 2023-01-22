package com.ds.money_manager.feature.main.fragment

import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsViewModel
import com.ds.money_manager.usecases.GetWalletsDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val signInRequestUseCase: GetWalletsDetailsUseCase,
) : DialogsViewModel() {

    fun getWalletsDetails() {
        launchUI {
            signInRequestUseCase().awaitFoldApi(
                {
                    val res = it
                },
                {
                    showError(it.title, it.description)
                },
                {
                    showError("", it.message!!)
                }
            )
        }
    }
}