package com.example.photoweather.photoslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoweather.R
import com.example.photoweather.data.cache.models.Photo
import com.example.photoweather.photoslist.adapter.PhotoViewHolder
import com.example.photoweather.photoslist.adapter.PhotosAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_photos_list.*
import kotlinx.android.synthetic.main.view_loading.*
import kotlinx.android.synthetic.main.view_no_results.*

class PhotosListFragment : Fragment(R.layout.fragment_photos_list), PhotosContract.View, PhotoViewHolder.Callback {

    private lateinit var adapter: PhotosAdapter

    private lateinit var presenter: PhotosContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        presenter = PhotosListPresenter(this)
    }

    private fun initView() {
        val layoutManager = GridLayoutManager(context, 2)
        rvPhotos.layoutManager = layoutManager

        adapter = PhotosAdapter(emptyList(), this)
        rvPhotos.adapter = adapter
    }

    override fun showNoResults() {
        llNoResults.visibility = View.VISIBLE
        llLoading.visibility = View.GONE
        rvPhotos.visibility = View.GONE
    }

    override fun showLoading(isLoading: Boolean) {
        llLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun showPhotos(photos: List<Photo>) {
        if (photos.isEmpty()) {
            showNoResults()
            return
        }

        llNoResults.visibility = View.GONE
        llLoading.visibility = View.GONE
        rvPhotos.visibility = View.VISIBLE

        adapter.updatePhotos(photos = photos)
    }

    override fun onPhotoSelected(photo: Photo) {
        this.view?.let {
            Snackbar.make(
                it,
                "You have clicked on photo: ${photo.city}, ${photo.country}",
                Snackbar.LENGTH_LONG
            )
        }
    }
}