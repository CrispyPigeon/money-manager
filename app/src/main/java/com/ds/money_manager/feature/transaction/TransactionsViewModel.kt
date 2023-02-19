package com.ds.money_manager.feature.transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ds.money_manager.base.helpers.awaitFoldApi
import com.ds.money_manager.base.helpers.launchUI
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.data.model.WalletInfo
import com.ds.money_manager.data.model.api.TransactionResponse
import com.ds.money_manager.extensions.toApiString
import com.ds.money_manager.usecases.GetTransactionsUseCase
import com.ds.money_manager.utils.LocalDateTimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    val getTransactionsUseCase: GetTransactionsUseCase
) : DialogsSupportViewModel() {

    val transactions = MutableLiveData<List<TransactionResponse>>()
    val walletInfo =  MutableLiveData<WalletInfo>()

    fun getTransaction() {
        launchUI {
            changeLoadingState(true)
            delay(1000)
            getTransactionsUseCase(
                viewModelScope,
                LocalDateTimeUtils.getAppMinimalDate().toApiString(),
                LocalDateTime.now().toApiString(),
                walletInfo.value!!.walletId,
                0,
                100
            ).awaitFoldApi(
                {
                    transactions.value = it
                },
                {
                    showError(it.title, it.description)
                },
                {
                    showError("", it.message.toString())
                }
            )
            changeLoadingState(false)
        }
    }

}