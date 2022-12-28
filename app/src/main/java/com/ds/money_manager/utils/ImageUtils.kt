package com.ds.money_manager.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ds.money_manager.R

object ImageUtils {
    fun loadPicture(context: Context, url: String?, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .error(R.drawable.ic_alert)
            .into(imageView)
    }

    fun loadLocalPicture(context: Context, imageId: Int, imageView: ImageView) {
        Glide.with(context)
            .load(imageId)
            .into(imageView)
    }
}