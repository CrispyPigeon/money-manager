package com.ds.money_manager.feature.signin

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.ds.money_manager.base.presentation.fragment.BaseFragment
import com.ds.money_manager.databinding.FragmentSignInBinding
import com.ds.money_manager.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>() {
    private var inputTypeClicked = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        //viewModel.signIn("string", "string")
    }

    private fun initListeners() {
        observe(viewModel.stateLiveData, ::onStateChanged)

        binding.edittextPassword.imageView.setOnClickListener {
            inputTypeClicked = !inputTypeClicked
            if (inputTypeClicked)
                binding.edittextPassword.editText.inputType = InputType.TYPE_CLASS_TEXT
            else
                binding.edittextPassword.editText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        binding.edittextLogin.addTextChangedListener {
            configureLoginButton()
        }

        binding.edittextPassword.editText.addTextChangedListener {
            configureLoginButton()
        }

        binding.buttonLogin.setOnClickListener {
            viewModel.signIn(
                binding.edittextLogin.text.toString(),
                binding.edittextPassword.editText.text.toString()
            )
        }
    }

    private fun configureLoginButton() {
        binding.buttonLogin.isEnabled =
            (binding.edittextLogin.text.length > 2 && binding.edittextPassword.editText.text.length > 2)
    }

    private fun onStateChanged(state: SignInViewModel.ViewState) {
        when (state){
            is SignInViewModel.ViewState.SignInSuccessful -> {
                Toast.makeText(requireContext(), "YES", Toast.LENGTH_LONG).show()
            }
            is SignInViewModel.ViewState.SignInFailure -> {
                Toast.makeText(requireContext(), "NO", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }
}