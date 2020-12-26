package com.example.photoweather.photoslist.adapter

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoweather.R
import com.example.photoweather.data.cache.models.Photo
import kotlinx.android.synthetic.main.view_item_photo.view.*

class PhotoViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bind(photo: Photo, callback: Callback) {
        Glide.with(itemView)
            .load(Uri.parse(photo.imagePath))
            .placeholder(R.drawable.ic_photo_placeholder)
            .into(itemView.ivPhoto)

        itemView.setOnClickListener { callback.onPhotoSelected(photo) }
    }

    interface Callback {
        fun onPhotoSelected(photo: Photo)
    }
}