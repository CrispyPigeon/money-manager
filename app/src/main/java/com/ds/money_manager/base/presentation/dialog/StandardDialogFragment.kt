package com.ds.money_manager.base.presentation.dialog

abstract class StandardDialogFragment : SizeFixedDialogFragment() {
    protected var title = ""
    protected var description = ""

    fun setText(title: String, description: String) {
        this.title = title
        this.description = description
    }
}