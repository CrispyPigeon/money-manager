package com.ds.money_manager.feature.intro

import android.os.Bundle
import android.view.View
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragments.BaseFragment
import com.ds.money_manager.databinding.FragmentIntroBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment : BaseFragment<FragmentIntroBinding, IntroViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.processAuth()
    }

    override fun initViews() {
    }

    override fun initListeners() {
        viewModel.signInSuccessEvent.observe(viewLifecycleOwner){
            navController.navigate(R.id.action_introFragment_to_mainFragment)
        }
        viewModel.signInErrorEvent.observe(viewLifecycleOwner){
            navController.navigate(R.id.action_introFragment_to_signInFragment)
        }
    }
}