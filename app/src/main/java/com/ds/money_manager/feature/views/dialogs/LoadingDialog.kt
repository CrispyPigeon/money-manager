package com.ds.money_manager.feature.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.dialogs.StandardDialogFragment


class LoadingDialog : StandardDialogFragment() {

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        titleTextView = requireView().findViewById(R.id.text_view_title)
        titleTextView.text = title
        descriptionTextView = requireView().findViewById(R.id.text_view_description)
        descriptionTextView.text = description
    }
}