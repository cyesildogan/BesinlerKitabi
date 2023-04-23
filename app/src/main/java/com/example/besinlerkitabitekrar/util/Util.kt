package com.example.besinlerkitabitekrar.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.besinlerkitabitekrar.R

fun ImageView.gorselIndir(url : String? , placeholder : CircularProgressDrawable){
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)


}

fun placeHolderYap(context : Context) :  CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        centerRadius = 40f
        strokeWidth = 8f
        start()
    }
}


@BindingAdapter("android:downloadimage")
fun downloadImage(view : ImageView, url : String?){
    view.gorselIndir(url, placeHolderYap(view.context))
}