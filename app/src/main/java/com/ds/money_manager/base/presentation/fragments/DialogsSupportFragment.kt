package com.ds.money_manager.base.presentation.fragments

import androidx.viewbinding.ViewBinding
import com.ds.money_manager.Constants.ERROR_DIALOG_TAG
import com.ds.money_manager.Constants.LOADING_DIALOG_TAG
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.feature.views.dialogs.AlertDialog
import com.ds.money_manager.feature.views.dialogs.LoadingDialog

abstract class DialogsSupportFragment<VB : ViewBinding, VM : DialogsSupportViewModel> :
    BaseFragment<VB, VM>() {

    var loadingDialog: LoadingDialog? = null
    var errorDialog: AlertDialog = AlertDialog()


    override fun initListeners() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                loadingDialog = LoadingDialog()
                loadingDialog!!.show(childFragmentManager, LOADING_DIALOG_TAG)
            } else {
                loadingDialog?.dismiss()
                loadingDialog = null
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog.setText(it.first, it.second)
            errorDialog.show(childFragmentManager, ERROR_DIALOG_TAG)
        }

        viewModel.errorWithIds.observe(viewLifecycleOwner) {
            errorDialog.setText(
                getString(it.first),
                getString(it.second)
            )
            errorDialog.show(childFragmentManager, ERROR_DIALOG_TAG)
        }
    }
}