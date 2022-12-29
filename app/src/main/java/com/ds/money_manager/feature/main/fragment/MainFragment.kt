package com.ds.money_manager.feature.main.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragment.BaseFragment
import com.ds.money_manager.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {
    override fun initViews() {
        configureAppBar()
    }

    private fun configureAppBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    override fun initListeners() {
        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.textViewTotalBalance.alpha =
                (appBarLayout.totalScrollRange + verticalOffset).toFloat() / appBarLayout.totalScrollRange
        }
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