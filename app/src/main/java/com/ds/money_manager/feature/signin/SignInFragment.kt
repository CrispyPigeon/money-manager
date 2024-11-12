package com.ds.money_manager.feature.signin

import android.text.InputType
import androidx.core.widget.addTextChangedListener
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragments.DialogsSupportFragment
import com.ds.money_manager.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : DialogsSupportFragment<FragmentSignInBinding, SignInViewModel>() {
    private var inputTypeClicked = false

    override fun initViews() {}

    override fun initListeners() {
        super.initListeners()

        binding.editTextPassword.imageView.setOnClickListener {
            inputTypeClicked = !inputTypeClicked
            if (inputTypeClicked)
                binding.editTextPassword.editText.inputType = InputType.TYPE_CLASS_TEXT
            else
                binding.editTextPassword.editText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        binding.editTextLogin.addTextChangedListener {
            configureLoginButton()
        }

        binding.editTextPassword.editText.addTextChangedListener {
            configureLoginButton()
        }

        binding.buttonLogin.setOnClickListener {
            viewModel.signIn(
                binding.editTextLogin.text.toString(),
                binding.editTextPassword.editText.text.toString()
            )
        }

        binding.textViewSignUp.setOnClickListener {
            navController.navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }

        viewModel.signInSuccessEvent.observe(viewLifecycleOwner) {
            navController.navigate(SignInFragmentDirections.actionSignInFragmentToMainFragment())
        }
    }

    private fun configureLoginButton() {
        binding.buttonLogin.isEnabled =
            (binding.editTextLogin.text.length > 2 && binding.editTextPassword.editText.text.length > 2)
    }
}