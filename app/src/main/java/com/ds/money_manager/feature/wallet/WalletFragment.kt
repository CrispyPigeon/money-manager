package com.ds.money_manager.feature.wallet

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.navArgs
import com.ds.money_manager.Constants.NEW_WALLET_ID
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragments.DialogsSupportFragment
import com.ds.money_manager.databinding.FragmentWalletBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : DialogsSupportFragment<FragmentWalletBinding, WalletViewModel>() {

    val args: WalletFragmentArgs by navArgs()

    override fun initViews() {
        super.initViews()

        loadingDialog.setText(
            getString(R.string.dialog_loading_title),
            getString(R.string.dialog_loading_data_description)
        )

        if (args.walletId != NEW_WALLET_ID) {
            configureDeleteButton()
            viewModel.getWalletData(args.walletId)
        }
    }

    override fun initListeners() {
        super.initListeners()

        binding.editTextWalletName.addTextChangedListener {
            configureContinueButton()
        }

        binding.buttonRemove.setOnClickListener {
            viewModel.removeWallet(args.walletId)
        }

        binding.buttonContinue.setOnClickListener {
            if (args.walletId == NEW_WALLET_ID)
                viewModel.saveWallet(binding.editTextWalletName.text.toString())
            else
                viewModel.updateWallet(args.walletId, binding.editTextWalletName.text.toString())
        }

        binding.imageViewBackButton.setOnClickListener {
            navController.navigateUp()
        }

        viewModel.wallet.observe(viewLifecycleOwner) {
            binding.editTextWalletName.setText(it.name)
        }

        viewModel.successEvent.observe(viewLifecycleOwner) {
            navController.navigateUp()
        }
    }

    private fun configureContinueButton() {
        binding.buttonContinue.isEnabled = binding.editTextWalletName.text.length > 2
    }

    private fun configureDeleteButton() {
        binding.buttonRemove.visibility = View.VISIBLE
    }
}