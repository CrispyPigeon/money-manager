package com.ds.money_manager.base.presentation.fragments

import androidx.viewbinding.ViewBinding
import com.ds.money_manager.Constants.ERROR_DIALOG_TAG
import com.ds.money_manager.Constants.LOADING_DIALOG_TAG
import com.ds.money_manager.base.presentation.viewmodels.DialogsViewModel
import com.ds.money_manager.feature.views.dialogs.AlertDialog
import com.ds.money_manager.feature.views.dialogs.LoadingDialog

abstract class DialogsSupportFragment<VB : ViewBinding, VM : DialogsViewModel> : BaseFragment<VB,VM> () {

    lateinit var loadingDialog: LoadingDialog
    lateinit var errorDialog: AlertDialog

    override fun initViews() {
        loadingDialog = LoadingDialog()
        errorDialog = AlertDialog()
    }

    override fun initListeners() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it)
                loadingDialog.show(requireActivity().supportFragmentManager, LOADING_DIALOG_TAG)
            else
                loadingDialog.dismiss()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog.setText(it.first, it.second)
            errorDialog.show(requireActivity().supportFragmentManager, ERROR_DIALOG_TAG)
        }

        viewModel.errorWithIds.observe(viewLifecycleOwner) {
            errorDialog.setText(
                getString(it.first),
                getString(it.second)
            )
            errorDialog.show(requireActivity().supportFragmentManager, ERROR_DIALOG_TAG)
        }
    }
}