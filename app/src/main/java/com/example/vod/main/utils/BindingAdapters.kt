package com.example.vod.main.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.vod.R

class BindingAdapters {

    companion object {
        @JvmStatic
        @BindingAdapter("app:srcUrl")
        fun setImageSourceFromUrl(imageView: ImageView, url: String?) {
            Glide.with(imageView.context)
                    .load(NetworkConstants.REQUEST_IMAGE_API + url)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView)
        }
    }
}