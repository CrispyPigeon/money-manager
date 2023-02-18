package com.ds.money_manager.feature.main.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE
import app.futured.donut.DonutSection
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragments.DialogsSupportFragment
import com.ds.money_manager.data.model.TransactionType
import com.ds.money_manager.data.model.api.StatisticItemResponse
import com.ds.money_manager.data.model.api.WalletDetailsResponse
import com.ds.money_manager.databinding.FragmentMainBinding
import com.ds.money_manager.feature.adapters.StatisticItemsAdapter
import com.ds.money_manager.feature.adapters.TransactionsAdapter
import com.ds.money_manager.feature.adapters.WalletsViewPagerAdapter
import com.ds.money_manager.helpers.AppBarStateChangeListener
import com.ds.money_manager.utils.CurrencyUtils
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : DialogsSupportFragment<FragmentMainBinding, MainViewModel>() {

    private val fabRotateStartAnimation: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.anim_rotate_start) }
    private val fabRotateCloseAnimation: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.anim_rotate_close) }
    private val fabShowAnimation: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.anim_fab_show) }
    private val fabHideAnimation: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.anim_fab_hide) }

    private var isFabPressed = false

    private var walletsAdapter: WalletsViewPagerAdapter? = null
    private var statisticItemsAdapter: StatisticItemsAdapter? = null
    private var transactionsAdapter: TransactionsAdapter? = null

    override fun initViews() {
        configureAppBar()
        configureSwipeRefreshLayout()
        configureWalletsRv()
        configureStatisticItemsRv()
        configureTransactionsRv()
        configureDiagram()
    }

    override fun initListeners() {
        super.initListeners()

        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.textViewTotalBalance.alpha =
                (appBarLayout.totalScrollRange + verticalOffset).toFloat() / appBarLayout.totalScrollRange
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getAllData()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.floatingActionButtonAdd.setOnClickListener {
            isFabPressed = !isFabPressed
            configureFloatViews(isFabPressed)
        }

        binding.textViewAddIncome.setOnClickListener {
            val action =
                MainFragmentDirections.actionMainFragmentToChooseWalletFragment(TransactionType.Income)
            navController.navigate(action)
        }

        binding.textViewAddCost.setOnClickListener {
            val action =
                MainFragmentDirections.actionMainFragmentToChooseWalletFragment(TransactionType.Cost)
            navController.navigate(action)
        }

        binding.viewPagerWallets.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (!binding.swipeRefreshLayout.isRefreshing) {
                    binding.swipeRefreshLayout.isEnabled = state == SCROLL_STATE_IDLE
                }
            }
        })

        walletsAdapter!!.onClickListener = { item, _ ->
            when (item) {
                is WalletDetailsResponse -> {
                    val action =
                        MainFragmentDirections.actionMainFragmentToWalletFragment(item.walletId)
                    navController.navigate(action)
                }
                else -> {
                    navController.navigate(R.id.action_mainFragment_to_walletFragment)
                }

            }
        }

        viewModel.totalBalance.observe(viewLifecycleOwner) {
            binding.collapsingToolbar.title = CurrencyUtils.showAmount(it)
        }

        viewModel.totalExpenses.observe(viewLifecycleOwner) {
            binding.textViewExpensesValue.text = CurrencyUtils.showAmount(it)
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

        viewModel.logOutSuccessEvent.observe(viewLifecycleOwner) {
            navController.navigate(R.id.action_mainFragment_to_signInFragment)
        }
    }

    private fun configureFloatViews(fabPressed: Boolean) {
        if (fabPressed) {
            binding.textViewAddIncome.visibility = View.VISIBLE
            binding.textViewAddIncome.startAnimation(fabShowAnimation)
            binding.textViewAddCost.visibility = View.VISIBLE
            binding.textViewAddCost.startAnimation(fabShowAnimation)
            binding.floatingActionButtonAdd.startAnimation(fabRotateStartAnimation)
        } else {
            binding.textViewAddIncome.visibility = View.GONE
            binding.textViewAddIncome.startAnimation(fabHideAnimation)
            binding.textViewAddCost.visibility = View.GONE
            binding.textViewAddCost.startAnimation(fabHideAnimation)
            binding.floatingActionButtonAdd.startAnimation(fabRotateCloseAnimation)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllData()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                viewModel.logOut()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
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

    private fun configureSwipeRefreshLayout() {
        binding.appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                if (state == State.EXPANDED)
                    binding.swipeRefreshLayout.setOnChildScrollUpCallback { parent, child -> false }
                else
                    binding.swipeRefreshLayout.setOnChildScrollUpCallback { parent, child -> true }
            }
        })
    }
}