package com.ds.money_manager.feature.income

import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragments.DialogsSupportFragment
import com.ds.money_manager.databinding.FragmentIncomeBinding
import com.ds.money_manager.extensions.loadLocalPicture
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IncomeFragment : DialogsSupportFragment<FragmentIncomeBinding, IncomeViewModel>() {

    private val CALENDAR_DP_SIZE = 22

    override fun initViews() {
        binding.editTextDate.imageView.loadLocalPicture(
            requireContext(),
            R.drawable.ic_calendar,
            getCalendarIconSize()
        )
        binding.editTextDate.editText.setHint(R.string.date_template)
    }

    private fun getCalendarIconSize(): Int {
        return (CALENDAR_DP_SIZE * requireContext().resources.displayMetrics.density).toInt()
    }

    override fun initListeners() {
        super.initListeners()

        binding.imageViewBackButton.setOnClickListener {
            navController.navigateUp()
        }
    }
}