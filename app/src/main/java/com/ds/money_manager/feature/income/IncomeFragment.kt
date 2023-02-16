package com.ds.money_manager.feature.income

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.text.InputType
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.fragments.DialogsSupportFragment
import com.ds.money_manager.databinding.FragmentIncomeBinding
import com.ds.money_manager.extensions.loadLocalPicture
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class IncomeFragment : DialogsSupportFragment<FragmentIncomeBinding, IncomeViewModel>() {
    private var isTodayDateSelected = false
    private val CALENDAR_DP_SIZE = 22

    override fun initViews() {
        binding.editTextDate.imageView.loadLocalPicture(
            requireContext(),
            R.drawable.ic_calendar,
            getCalendarIconSize()
        )
        binding.editTextDate.editText.isFocusable = false
        binding.editTextDate.editText.inputType = InputType.TYPE_CLASS_DATETIME
        binding.editTextDate.editText.setHint(R.string.date_template)
    }

    override fun initListeners() {
        super.initListeners()

        binding.editTextDate.editText.setOnClickListener {
            showCalendar()
        }

        binding.editTextDate.editText.addTextChangedListener {
            configureContinueButton()
        }

        binding.imageViewBackButton.setOnClickListener {
            navController.navigateUp()
        }

        binding.editTextDate.imageView.setOnClickListener {
            showCalendar()
        }

        binding.editTextName.addTextChangedListener {
            configureContinueButton()
        }

        binding.editTextAmount.addTextChangedListener {
            configureContinueButton()
        }

        binding.constraintLayoutTodayDate.setOnClickListener {
            isTodayDateSelected = !isTodayDateSelected
            configureDate(isTodayDateSelected)
        }
    }

    private fun configureDate(todayDateSelected: Boolean) {
        if (todayDateSelected)
        {

        }
        else
        {

        }
    }

    fun showCalendar(){
        val c: Calendar = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
//                binding.editTextDate.editText.setText(
//                    dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
//                )
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }

    private fun getCalendarIconSize(): Int {
        return (CALENDAR_DP_SIZE * requireContext().resources.displayMetrics.density).toInt()
    }

    private fun configureContinueButton() {
        binding.buttonContinue.isEnabled = binding.editTextName.text.length > 2 && binding.editTextName.text.isNotEmpty() && binding.editTextDate.editText.text.length == 9
    }
}