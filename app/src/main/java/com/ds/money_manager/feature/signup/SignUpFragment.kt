package com.ds.money_manager.feature.signup

import android.os.Bundle
import android.view.View
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragment.BaseFragment
import com.ds.money_manager.base.presentation.fragment.DialogsSupportFragment
import com.ds.money_manager.databinding.FragmentSignUpBinding
import com.ds.money_manager.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : DialogsSupportFragment<FragmentSignUpBinding, SignUpViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
    }

    override fun initViews() {
        binding.edittextRepeatPassword.editText.hint = getString(R.string.repeat_password)
    }

    override fun initListeners() {
        binding.textviewLogIn.setOnClickListener {
            navController.navigateUp()
        }
    }
}