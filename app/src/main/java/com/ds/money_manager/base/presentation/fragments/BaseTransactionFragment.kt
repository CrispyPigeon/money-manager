package com.ds.money_manager.base.presentation.fragments

import android.app.DatePickerDialog
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.viewmodels.DialogsSupportViewModel
import com.ds.money_manager.extensions.loadLocalPicture
import com.ds.money_manager.utils.SizeConvertersUtils
import java.time.LocalDateTime
import java.util.*

abstract class BaseTransactionFragment<VB : ViewBinding, VM : DialogsSupportViewModel> :
    DialogsSupportFragment<VB, VM>() {
    protected var isTodayDateSelected = false
    protected val CALENDAR_DP_SIZE = 22
    protected val DONE_DP_SIZE = 28

    protected fun showCalendar(date: MutableLiveData<LocalDateTime>) {
        if (isTodayDateSelected)
            return

        val c: Calendar = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                date.value = LocalDateTime.now()
                    .withYear(year)
                    .withMonth(monthOfYear + 1)
                    .withDayOfMonth(dayOfMonth)

            }, mYear, mMonth, mDay
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    protected fun configureDate(
        todayDateSelected: Boolean,
        imageView: ImageView,
        date: MutableLiveData<LocalDateTime>
    ) {
        if (todayDateSelected) {
            date.value = LocalDateTime.now()
            imageView.loadLocalPicture(
                requireContext(),
                R.drawable.ic_done,
                SizeConvertersUtils.toPx(requireContext(), DONE_DP_SIZE)
            )
        } else {
            imageView.loadLocalPicture(
                requireContext(),
                R.drawable.ic_not_done,
                SizeConvertersUtils.toPx(requireContext(), DONE_DP_SIZE)
            )
        }
    }
}