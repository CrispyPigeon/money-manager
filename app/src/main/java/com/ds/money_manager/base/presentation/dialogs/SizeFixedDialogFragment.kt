package com.ds.money_manager.base.presentation.dialogs

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ds.money_manager.R

open class SizeFixedDialogFragment : DialogFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setupDialogSize()
    }

    private fun setupDialogSize() {
        val window = if (dialog != null) dialog!!.window else null

        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val dialogWidthByWidowWidth = -1
        val dialogWidthByWindowMinMargin: Int
        val dialogMinMarginStartEndDimenRes = R.dimen.margin_general_medium
        val dialogMinMarginStartEnd =
            resources.getDimensionPixelSize(dialogMinMarginStartEndDimenRes)
        dialogWidthByWindowMinMargin = screenWidth - 2 * dialogMinMarginStartEnd

        var dialogWidth = -1
        if (dialogWidthByWidowWidth > 0 && dialogWidthByWindowMinMargin > 0) {
            dialogWidth = Math.min(dialogWidthByWidowWidth, dialogWidthByWindowMinMargin)
        } else if (dialogWidthByWidowWidth > 0) {
            if (dialogWidthByWidowWidth <= screenWidth) {
                dialogWidth = dialogWidthByWidowWidth
            }
        } else if (dialogWidthByWindowMinMargin > 0) {
            dialogWidth = dialogWidthByWindowMinMargin
        }
        if (dialogWidth > 0) {
            val dialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT
            window!!.setLayout(dialogWidth, dialogHeight)
        }

    }
}