package com.devmina.droid_ant.colorit

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devmina.droid_ant.colorit.color_canvas.ImageAdapter
import com.devmina.droid_ant.colorit.model.ListofImages



@BindingAdapter("listImg")
fun bindImageRecuclerView(recyclerView: RecyclerView, data: List<ListofImages>?){
    val adapter =  recyclerView.adapter as ImageAdapter
    adapter.submitList(data)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, img: Int) {
    img.let {
        //val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(img)
            .into(imgView)
    }
}

