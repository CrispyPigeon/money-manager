package com.ds.money_manager.feature.transaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.ds.money_manager.Constants.WALLET_DATA
import com.ds.money_manager.Constants.WALLET_DATA_REQUEST_KEY
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragments.DialogsSupportFragment
import com.ds.money_manager.data.model.WalletInfo
import com.ds.money_manager.databinding.FragmentTransactionsBinding
import com.ds.money_manager.feature.adapters.TransactionsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsFragment :
    DialogsSupportFragment<FragmentTransactionsBinding, TransactionsViewModel>() {

    private var transactionsAdapter: TransactionsAdapter? = null


    override fun initViews() {
        configureTransactionsRv()
    }

    override fun initListeners() {
        super.initListeners()

        binding.imageViewBackButton.setOnClickListener {
            navController.navigateUp()
        }

        binding.textViewWallet.setOnClickListener {
            setFragmentResultListener(WALLET_DATA_REQUEST_KEY) { key, bundle ->
                val result = bundle.getSerializable(WALLET_DATA) as WalletInfo?
                if (result != null)
                    viewModel.walletInfo.value = result
            }
            navController.navigate(R.id.action_transactionsFragment_to_chooseCombinedWalletFragment)
        }

        viewModel.walletInfo.observe(viewLifecycleOwner) {
            binding.textViewWallet.text = it.name
            viewModel.getTransaction()
        }

        viewModel.transactions.observe(viewLifecycleOwner) {
            transactionsAdapter!!.setItems(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.walletInfo.value = WalletInfo(null, getString(R.string.all_wallet))
    }

    private fun configureTransactionsRv() {
        transactionsAdapter = TransactionsAdapter(requireContext())
        binding.recyclerViewTransactionItems.adapter = transactionsAdapter
        binding.recyclerViewTransactionItems.layoutManager = LinearLayoutManager(requireContext())
    }
}