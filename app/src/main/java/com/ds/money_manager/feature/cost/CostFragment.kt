package com.ds.money_manager.feature.cost

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.navArgs
import com.ds.money_manager.Constants
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragments.BaseTransactionFragment
import com.ds.money_manager.databinding.FragmentCostBinding
import com.ds.money_manager.extensions.loadLocalPicture
import com.ds.money_manager.extensions.toFormattedString
import com.ds.money_manager.utils.CurrencyUtils
import com.ds.money_manager.utils.SizeConvertersUtils
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal

@AndroidEntryPoint
class CostFragment : BaseTransactionFragment<FragmentCostBinding, CostViewModel>() {

    val args: CostFragmentArgs by navArgs()

    override fun initViews() {
        binding.editTextDate.imageView.loadLocalPicture(
            requireContext(),
            R.drawable.ic_calendar,
            SizeConvertersUtils.toPx(requireContext(), CALENDAR_DP_SIZE)
        )
        binding.editTextDate.editText.isFocusable = false
        binding.editTextDate.editText.inputType = InputType.TYPE_CLASS_DATETIME
        binding.editTextDate.editText.setHint(R.string.date_template)
    }

    override fun initListeners() {
        super.initListeners()

        binding.editTextDate.editText.setOnClickListener {
            showCalendar(viewModel.localeDate)
        }

        binding.editTextDate.editText.addTextChangedListener {
            configureContinueButton()
        }

        binding.imageViewBackButton.setOnClickListener {
            navController.navigateUp()
        }

        binding.editTextDate.imageView.setOnClickListener {
            showCalendar(viewModel.localeDate)
        }

        binding.editTextName.addTextChangedListener {
            configureContinueButton()
        }

        binding.editTextAmount.addTextChangedListener {
            configureContinueButton()
        }

        binding.constraintLayoutTodayDate.setOnClickListener {
            isTodayDateSelected = !isTodayDateSelected
            configureDate(isTodayDateSelected, binding.imageViewSelector, viewModel.localeDate)
        }

        binding.buttonContinue.setOnClickListener {
            if (args.costId == Constants.ITEM_ID)
                viewModel.saveCost(
                    binding.editTextName.text.toString(),
                    BigDecimal(binding.editTextAmount.text.toString()),
                    args.walletId,
                    args.costTypeId
                )
            else
                viewModel.updateCost(
                    binding.editTextName.text.toString(),
                    BigDecimal(binding.editTextAmount.text.toString())
                )
        }

        binding.buttonRemove.setOnClickListener {
            viewModel.removeCost(args.costId)
        }

        viewModel.successEvent.observe(viewLifecycleOwner) {
            navController.navigate(CostFragmentDirections.actionCostFragmentToMainFragment())
        }

        viewModel.localeDate.observe(viewLifecycleOwner) {
            binding.editTextDate.editText.setText(
                it.toFormattedString()
            )
        }

        viewModel.cost.observe(viewLifecycleOwner) {
            binding.editTextName.setText(it.name)
            binding.editTextAmount.setText(it.sum.toPlainString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.costId != Constants.ITEM_ID) {
            configureDeleteButton()
            viewModel.getCost(args.costId)
        }
    }

    private fun configureContinueButton() {
        binding.buttonContinue.isEnabled =
            binding.editTextName.text.length > 2 && binding.editTextAmount.text.isNotEmpty() && binding.editTextDate.editText.text.length > 6
    }

    private fun configureDeleteButton() {
        binding.buttonRemove.visibility = View.VISIBLE
    }
}