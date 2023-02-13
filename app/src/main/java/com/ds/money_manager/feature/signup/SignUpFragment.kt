package com.ds.money_manager.feature.signup

import android.text.InputType
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragments.DialogsSupportFragment
import com.ds.money_manager.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : DialogsSupportFragment<FragmentSignUpBinding, SignUpViewModel>() {

    private var inputTypeForPasswordClicked = false
    private var inputTypeForRepeatPasswordClicked = false

    override fun initViews() {
        binding.editTextPassword.editText.imeOptions = EditorInfo.IME_ACTION_NEXT
        binding.editTextPassword.editText.nextFocusDownId = binding.editTextRepeatPassword.id
        binding.editTextRepeatPassword.editText.hint = getString(R.string.repeat_password)
    }

    override fun initListeners() {
        super.initListeners()

        binding.buttonSignUp.setOnClickListener {
            viewModel.signUp(
                binding.editTextLogin.text.toString(),
                binding.editTextPassword.editText.text.toString(),
                binding.editTextRepeatPassword.editText.text.toString()
            )
        }

        binding.textViewLogIn.setOnClickListener {
            navController.navigateUp()
        }

        binding.editTextPassword.imageView.setOnClickListener {
            inputTypeForPasswordClicked = !inputTypeForPasswordClicked
            if (inputTypeForPasswordClicked)
                binding.editTextPassword.editText.inputType = InputType.TYPE_CLASS_TEXT
            else
                binding.editTextPassword.editText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        binding.editTextRepeatPassword.imageView.setOnClickListener {
            inputTypeForRepeatPasswordClicked = !inputTypeForRepeatPasswordClicked
            if (inputTypeForRepeatPasswordClicked)
                binding.editTextRepeatPassword.editText.inputType = InputType.TYPE_CLASS_TEXT
            else
                binding.editTextRepeatPassword.editText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        binding.editTextLogin.addTextChangedListener {
            configureLoginButton()
        }

        binding.editTextPassword.editText.addTextChangedListener {
            configureLoginButton()
        }

        binding.editTextRepeatPassword.editText.addTextChangedListener {
            configureLoginButton()
        }

        viewModel.authSuccessEvent.observe(viewLifecycleOwner) {
            navController.navigate(R.id.action_signUpFragment_to_mainFragment)
        }
    }

    private fun configureLoginButton() {
        binding.buttonSignUp.isEnabled =
            (binding.editTextLogin.text.length > 2 &&
                    binding.editTextPassword.editText.text.length > 2 &&
                    binding.editTextRepeatPassword.editText.text.length > 2)
    }
}