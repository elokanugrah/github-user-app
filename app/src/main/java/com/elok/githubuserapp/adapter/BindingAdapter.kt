package com.elok.githubuserapp.adapter

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.elok.githubuserapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_image_placeholder)
            .into(view)
    }
}

@BindingAdapter("imageCircleFromUrl")
fun bindImageCircleFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_image_placeholder)
            .into(view)
    }
}

@BindingAdapter("isVisible")
fun isVisible(view: View, string: String?) {
    view.visibility = if (string.isNullOrEmpty()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("isFabLoved")
fun isFabLoved(view: FloatingActionButton, isLoved: Boolean?) {
    if (isLoved == null || !isLoved) {
        view.supportImageTintList = ContextCompat.getColorStateList(view.context, R.color.colorPrimaryDark)
    } else {
        view.supportImageTintList = ContextCompat.getColorStateList(view.context, R.color.loveButton)
    }
}