package com.zerir.robusta.presentation.utils

import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["imageUrl", "placeHolder", "errorHolder"])
fun ImageView.loadImage(
    imageUrl: String?,
    placeHolder: Int?,
    errorHolder: Int?,
) {
    Glide.with(context)
        .load(imageUrl)
        .placeholder(if(placeHolder != null) ActivityCompat.getDrawable(context, placeHolder) else null)
        .error(if(errorHolder != null) ActivityCompat.getDrawable(context, errorHolder) else null)
        .into(this)
}