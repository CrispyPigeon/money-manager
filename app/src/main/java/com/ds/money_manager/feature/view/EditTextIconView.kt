package com.ds.money_manager.feature.view

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ds.money_manager.R

class EditTextIconView(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    val editText: EditText
    val imageView: ImageView

    init {
        inflate(context, R.layout.view_edit_text_icon, this)
        editText = findViewById(R.id.edit_text)
        imageView = findViewById(R.id.image_view)
    }
}