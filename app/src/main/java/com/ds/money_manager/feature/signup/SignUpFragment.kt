package com.ds.money_manager.feature.signup

import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragment.DialogsSupportFragment
import com.ds.money_manager.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : DialogsSupportFragment<FragmentSignUpBinding, SignUpViewModel>() {

    private var inputTypeForPasswordClicked = false
    private var inputTypeForRepeatPasswordClicked = false

    override fun initViews() {
        super.initViews()

        loadingDialog.setText(
            getString(R.string.dialog_loading_title),
            getString(R.string.dialog_loading_sign_in_description)
        )
        binding.edittextPassword.editText.imeOptions = EditorInfo.IME_ACTION_NEXT
        binding.edittextPassword.editText.nextFocusDownId = binding.edittextRepeatPassword.id
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

        binding.edittextPassword.imageView.setOnClickListener {
            inputTypeForPasswordClicked = !inputTypeForPasswordClicked
            if (inputTypeForPasswordClicked)
                binding.edittextPassword.editText.inputType = InputType.TYPE_CLASS_TEXT
            else
                binding.edittextPassword.editText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        binding.edittextRepeatPassword.imageView.setOnClickListener {
            inputTypeForRepeatPasswordClicked = !inputTypeForRepeatPasswordClicked
            if (inputTypeForRepeatPasswordClicked)
                binding.edittextRepeatPassword.editText.inputType = InputType.TYPE_CLASS_TEXT
            else
                binding.edittextRepeatPassword.editText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        binding.edittextLogin.addTextChangedListener {
            configureLoginButton()
        }

        binding.edittextPassword.editText.addTextChangedListener {
            configureLoginButton()
        }

        binding.edittextRepeatPassword.editText.addTextChangedListener {
            configureLoginButton()
        }

        viewModel.authSuccessEvent.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "YES", Toast.LENGTH_LONG).show()
        }
    }

    private fun configureLoginButton() {
        binding.buttonSignUp.isEnabled =
            (binding.edittextLogin.text.length > 2 &&
                    binding.edittextPassword.editText.text.length > 2 &&
                    binding.edittextRepeatPassword.editText.text.length > 2)
    }
}