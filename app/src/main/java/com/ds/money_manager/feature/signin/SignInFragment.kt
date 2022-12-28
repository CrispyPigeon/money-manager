package com.ds.money_manager.feature.signin

import android.text.InputType
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragment.DialogsSupportFragment
import com.ds.money_manager.databinding.FragmentSignInBinding
import com.ds.money_manager.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : DialogsSupportFragment<FragmentSignInBinding, SignInViewModel>() {
    private var inputTypeClicked = false

    override fun initViews() {
        super.initViews()

        loadingDialog.setText(
            getString(R.string.dialog_loading_title),
            getString(R.string.dialog_loading_sign_in_description)
        )
    }

    override fun initListeners() {
        super.initListeners()

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

        binding.textviewSignUp.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        viewModel.signInSuccessEvent.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "YES", Toast.LENGTH_LONG).show()
        }
    }

    private fun configureLoginButton() {
        binding.buttonLogin.isEnabled =
            (binding.edittextLogin.text.length > 2 && binding.edittextPassword.editText.text.length > 2)
    }
}