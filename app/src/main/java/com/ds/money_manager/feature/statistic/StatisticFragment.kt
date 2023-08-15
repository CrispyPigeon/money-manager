package com.ds.money_manager.feature.statistic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.ds.money_manager.Constants
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragments.DialogsSupportFragment
import com.ds.money_manager.data.model.WalletInfo
import com.ds.money_manager.databinding.FragmentStatisticsBinding
import com.ds.money_manager.feature.adapters.StatisticItemsAdapter
import com.ds.money_manager.feature.transaction.TransactionsFragmentDirections
import com.ds.money_manager.utils.CurrencyUtils
import com.ds.money_manager.utils.StatisticDiagramUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticFragment : DialogsSupportFragment<FragmentStatisticsBinding, StatisticViewModel>() {

    private var statisticItemsAdapter: StatisticItemsAdapter? = null

    override fun initViews() {
        configureStatisticItemsRv()
        configureDiagram()
    }

    override fun initListeners() {
        super.initListeners()

        binding.imageViewBackButton.setOnClickListener {
            navController.navigateUp()
        }

        binding.textViewWallet.setOnClickListener {
            setFragmentResultListener(Constants.WALLET_DATA_REQUEST_KEY) { key, bundle ->
                val result = bundle.getSerializable(Constants.WALLET_DATA) as WalletInfo?
                if (result != null) {
                    viewModel.walletInfo.value = result
                }
            }
            navController.navigate(StatisticFragmentDirections.actionStatisticFragmentToChooseCombinedWalletFragment())
        }

        binding.imageViewMonthBack.setOnClickListener {
            viewModel.previousMonth()
        }

        binding.imageViewMonthNext.setOnClickListener {
            viewModel.nextMonth()
        }

        viewModel.monthName.observe(viewLifecycleOwner) {
            binding.textViewMonthStatistic.text = it
        }

        viewModel.totalExpenses.observe(viewLifecycleOwner) {
            binding.textViewExpensesValue.text = CurrencyUtils.showAmount(it)
        }

        viewModel.totalStatisticData.observe(viewLifecycleOwner) {
            val sections = StatisticDiagramUtils.setDiagramData(it)
            binding.donutViewStatistic.submitData(sections)
            statisticItemsAdapter!!.setItems(it)
        }

        viewModel.walletInfo.observe(viewLifecycleOwner) {
            binding.textViewWallet.text = it.name
            viewModel.getStatisticData()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.walletInfo.value = WalletInfo(null, getString(R.string.all_wallet))
    }

    private fun configureStatisticItemsRv() {
        statisticItemsAdapter = StatisticItemsAdapter(requireContext())
        binding.recyclerViewStatisticItems.adapter = statisticItemsAdapter
        binding.recyclerViewStatisticItems.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun configureDiagram() {
        binding.donutViewStatistic.cap = 5f
    }

}