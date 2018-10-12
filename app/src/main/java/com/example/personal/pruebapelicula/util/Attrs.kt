package com.example.personal.pruebapelicula.util

import android.databinding.BindingAdapter
import android.net.Uri
import android.widget.ImageView
import com.squareup.picasso.Picasso

@BindingAdapter("app:imgUrl")
fun setImageUrl(img: ImageView, url: String?) {
    if (url!=null) {
        Picasso.get()
                .load(Uri.parse("https://image.tmdb.org/t/p/w500$url"))
                .into(img)
    }
}