package com.ds.money_manager.base.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class DialogsSupportViewModel : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Pair<String,String>>()
    val errorWithIds = MutableLiveData<Pair<Int,Int>>()
    val confirm = MutableLiveData<Pair<String,String>>()

    fun changeLoadingState(flag: Boolean){
        loading.value = flag
    }

    fun showError(title: String, description: String){
        error.value = Pair(title, description)
    }

    fun showErrorWithIds(titleId: Int, descriptionId: Int){
        errorWithIds.value = Pair(titleId, descriptionId)
    }

    fun showConfirm(title: String, description: String){
        confirm.value = Pair(title, description)
    }
}