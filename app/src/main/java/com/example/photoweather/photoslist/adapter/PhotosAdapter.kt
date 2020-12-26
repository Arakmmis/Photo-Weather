package com.example.photoweather.photoslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoweather.R
import com.example.photoweather.data.cache.models.Photo

class PhotosAdapter(var photos: List<Photo>, private val callback: PhotoViewHolder.Callback) :
    RecyclerView.Adapter<PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_photo, parent, false)

        return PhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position], callback)
    }

    override fun getItemCount() = photos.size

    fun updatePhotos(photos: List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }
}