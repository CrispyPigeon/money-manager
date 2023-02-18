package com.ds.money_manager.feature.wallet.choose

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ds.money_manager.base.presentation.fragments.DialogsSupportFragment
import com.ds.money_manager.data.model.TransactionType
import com.ds.money_manager.databinding.FragmentChooseWalletBinding
import com.ds.money_manager.feature.adapters.WalletsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseWalletFragment :
    DialogsSupportFragment<FragmentChooseWalletBinding, ChooseWalletViewModel>() {

    val args: ChooseWalletFragmentArgs by navArgs()
    var walletsAdapter: WalletsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getWalletsDetails()
    }

    override fun initViews() {
        configureWalletRv()
    }

    override fun initListeners() {
        super.initListeners()

        binding.imageViewBackButton.setOnClickListener {
            navController.navigateUp()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getWalletsDetails()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        walletsAdapter?.onClickListener = { item, position ->
            val action =
                when (args.transactionType) {
                    TransactionType.Cost ->
                        ChooseWalletFragmentDirections.actionChooseWalletFragmentToChooseCostTypeFragment(
                            item.walletId
                        )
                    TransactionType.Income ->
                        ChooseWalletFragmentDirections.actionChooseWalletFragmentToIncomeFragment(
                            item.walletId
                        )
                }

            navController.navigate(action)
        }

        viewModel.wallets.observe(viewLifecycleOwner) {
            walletsAdapter?.setItems(it)
        }
    }

    private fun configureWalletRv() {
        walletsAdapter = WalletsAdapter(requireContext())
        binding.recyclerViewWallets.adapter = walletsAdapter
        binding.recyclerViewWallets.layoutManager = LinearLayoutManager(requireContext())
    }
}