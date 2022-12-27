package com.ds.money_manager.feature.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ds.money_manager.R
import com.ds.money_manager.base.presentation.view.SizeFixedDialogFragment


class LoadingDialog : SizeFixedDialogFragment() {

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

    fun setText(title: String, description: String) {
        titleTextView.text = title
        descriptionTextView.text = description
    }

    private fun initViews() {
        titleTextView = requireView().findViewById(R.id.textview_title)
        descriptionTextView = requireView().findViewById(R.id.textview_description)
    }
}