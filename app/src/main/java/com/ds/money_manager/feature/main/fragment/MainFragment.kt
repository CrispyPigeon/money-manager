package com.ds.money_manager.feature.main.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import app.futured.donut.DonutSection
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragments.DialogsSupportFragment
import com.ds.money_manager.data.model.api.StatisticItemResponse
import com.ds.money_manager.databinding.FragmentMainBinding
import com.ds.money_manager.feature.adapters.StatisticItemsAdapter
import com.ds.money_manager.feature.adapters.TransactionsAdapter
import com.ds.money_manager.feature.adapters.WalletsViewPagerAdapter
import com.ds.money_manager.utils.CurrencyUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : DialogsSupportFragment<FragmentMainBinding, MainViewModel>() {

    private var walletsAdapter: WalletsViewPagerAdapter? = null
    private var statisticItemsAdapter: StatisticItemsAdapter? = null
    private var transactionsAdapter: TransactionsAdapter? = null

    override fun initViews() {
        configureAppBar()
        configureWalletsRv()
        configureStatisticItemsRv()
        configureTransactionsRv()
        configureDiagram()
    }

    override fun initListeners() {
        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.textViewTotalBalance.alpha =
                (appBarLayout.totalScrollRange + verticalOffset).toFloat() / appBarLayout.totalScrollRange
        }

        viewModel.totalBalance.observe(viewLifecycleOwner) {
            binding.collapsingToolbar.title = CurrencyUtils.showAmount(it)
        }

        viewModel.wallets.observe(viewLifecycleOwner) {
            walletsAdapter!!.setItems(it)
        }

        viewModel.totalStatisticData.observe(viewLifecycleOwner) {
            setDiagramData(it)
            statisticItemsAdapter!!.setItems(it)
        }

        viewModel.transactions.observe(viewLifecycleOwner) {
            transactionsAdapter!!.setItems(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTotalBalance()
        viewModel.getWalletsDetails()
        viewModel.getStatisticData()
        viewModel.getLastTransactions()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setDiagramData(statisticItems: List<StatisticItemResponse>) {
        val sections = mutableListOf<DonutSection>()

        statisticItems.forEach {
            sections.add(
                DonutSection(
                    name = it.name,
                    color = Color.parseColor(it.color),
                    amount = it.amount.toFloat()
                )
            )
        }

        binding.donutViewStatistic.submitData(sections)
    }

    private fun configureDiagram() {
        binding.donutViewStatistic.cap = 5f
    }

    private fun configureWalletsRv() {
        walletsAdapter = WalletsViewPagerAdapter()
        binding.viewPagerWallets.clipToPadding = false
        binding.viewPagerWallets.clipChildren = false
        binding.viewPagerWallets.offscreenPageLimit = 3
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.margin_general)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.margin_view_pager)
        binding.viewPagerWallets.setPageTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }
        binding.viewPagerWallets.adapter = walletsAdapter
    }

    private fun configureTransactionsRv() {
        transactionsAdapter = TransactionsAdapter(requireContext())
        binding.recyclerViewTransactionItems.adapter = transactionsAdapter
        binding.recyclerViewTransactionItems.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun configureStatisticItemsRv() {
        statisticItemsAdapter = StatisticItemsAdapter(requireContext())
        binding.recyclerViewStatisticItems.adapter = statisticItemsAdapter
        binding.recyclerViewStatisticItems.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun configureAppBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }
}