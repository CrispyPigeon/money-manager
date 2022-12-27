package com.ds.money_manager.base.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class DialogsViewModel : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Pair<String,String>>()
    val confirm = MutableLiveData<Pair<String,String>>()

    fun changeLoadingState(flag: Boolean){
        loading.value = flag
    }

    fun showError(title: String, description: String){
        error.value = Pair(title, description)
    }

    fun showConfirm(title: String, description: String){
        confirm.value = Pair(title, description)
    }
}