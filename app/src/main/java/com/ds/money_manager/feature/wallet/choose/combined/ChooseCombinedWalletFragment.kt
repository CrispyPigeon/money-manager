package com.ds.money_manager.feature.wallet.choose.combined

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.ds.money_manager.Constants.ALL_WALLETS_VALUE
import com.ds.money_manager.Constants.WALLET_DATA
import com.ds.money_manager.Constants.WALLET_DATA_REQUEST_KEY
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragments.DialogsSupportFragment
import com.ds.money_manager.data.model.WalletInfo
import com.ds.money_manager.databinding.FragmentChooseWalletBinding
import com.ds.money_manager.feature.adapters.WalletsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseCombinedWalletFragment :
    DialogsSupportFragment<FragmentChooseWalletBinding, ChooseCombinedWalletViewModel>() {

    var walletsAdapter: WalletsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getWalletsDetails(getString(R.string.all_wallet))
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
            viewModel.getWalletsDetails(getString(R.string.all_wallet))
            binding.swipeRefreshLayout.isRefreshing = false
        }

        walletsAdapter?.onClickListener = { item, position ->
            val bundle = Bundle()
            val walledId = if (item.walletId == ALL_WALLETS_VALUE) null else item.walletId
            bundle.putSerializable(WALLET_DATA, WalletInfo(walledId, item.name))
            setFragmentResult(WALLET_DATA_REQUEST_KEY, bundle)
            navController.navigateUp()
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