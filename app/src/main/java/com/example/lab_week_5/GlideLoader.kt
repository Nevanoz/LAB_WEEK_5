package com.example.lab_week_5

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideLoader : ImageLoader {

    override fun loadImage(imageUrl: String, imageView: ImageView) {
        // Aman karena terkait langsung dengan View lifecycle
        Glide.with(imageView)
            .load(imageUrl)
            .centerCrop()
            .into(imageView)
    }
}
