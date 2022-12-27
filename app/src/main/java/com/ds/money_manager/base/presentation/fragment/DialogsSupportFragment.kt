package com.ds.money_manager.base.presentation.fragment

import androidx.viewbinding.ViewBinding
import com.ds.money_manager.base.presentation.viewmodels.DialogsViewModel
import com.ds.money_manager.feature.view.LoadingDialog

abstract class DialogsSupportFragment<VB : ViewBinding, VM : DialogsViewModel> : BaseFragment<VB,VM> () {

    lateinit var loadingDialog: LoadingDialog

    override fun initViews() {
        super.initViews()

        loadingDialog = LoadingDialog()
    }

    override fun initListeners() {
        viewModel.loading.observe(viewLifecycleOwner){
            if (it)
                loadingDialog.show(requireActivity().supportFragmentManager,"LOADING_DIALOG_TAG")
            else
                loadingDialog.dismiss()
        }
    }
}