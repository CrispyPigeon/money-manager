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

    override fun initViews() {
        super.initViews()

        loadingDialog.setText(
            getString(R.string.dialog_loading_title),
            getString(R.string.dialog_loading_sign_in_description)
        )

        binding.edittextRepeatPassword.editText.hint = getString(R.string.repeat_password)
    }

    override fun initListeners() {
        super.initListeners()

        binding.buttonSignUp.setOnClickListener {
            viewModel.signUp(
                binding.edittextLogin.text.toString(),
                binding.edittextPassword.editText.text.toString(),
                binding.edittextRepeatPassword.editText.text.toString()
            )
        }

        binding.textviewLogIn.setOnClickListener {
            navController.navigateUp()
        }
    }
}