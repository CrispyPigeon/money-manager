package com.ds.money_manager.feature.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.dialogs.StandardDialogFragment

class AlertDialog : StandardDialogFragment() {
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var textViewButton: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
    }

    private fun initViews() {
        if (title.isNotEmpty()) {
            titleTextView = requireView().findViewById(R.id.text_view_title)
            titleTextView.text = title
        }
        if (description.isNotEmpty()) {
            descriptionTextView = requireView().findViewById(R.id.text_view_description)
            descriptionTextView.text = description
        }
        textViewButton = requireView().findViewById(R.id.textview_button)
    }

    private fun initListeners() {
        textViewButton.setOnClickListener {
            this.dismiss()
        }
    }
}