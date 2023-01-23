package com.ds.money_manager.feature.main.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragments.DialogsSupportFragment
import com.ds.money_manager.databinding.FragmentMainBinding
import com.ds.money_manager.feature.main.fragment.adapters.WalletsViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : DialogsSupportFragment<FragmentMainBinding, MainViewModel>() {

    private var walletsAdapter: WalletsViewPagerAdapter? = null

    override fun initViews() {
        configureAppBar()
        configureWalletsRv()
    }

    override fun initListeners() {
        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.textViewTotalBalance.alpha =
                (appBarLayout.totalScrollRange + verticalOffset).toFloat() / appBarLayout.totalScrollRange
        }

        viewModel.wallets.observe(viewLifecycleOwner) {
            walletsAdapter!!.setItems(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getWalletsDetails()
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

    private fun configureAppBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
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
}