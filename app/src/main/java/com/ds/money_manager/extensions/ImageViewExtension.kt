package com.ds.money_manager.extensions

import android.content.Context
import android.widget.ImageView
import com.ds.money_manager.utils.ImageUtils

fun ImageView.loadPicture(context: Context, url: String?) {
    ImageUtils.loadPicture(context, url, this)
}

fun ImageView.loadLocalPicture(context: Context, imageId: Int) {
    ImageUtils.loadLocalPicture(context, imageId, this)
}