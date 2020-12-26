package com.example.photoweather.photoslist

import com.example.photoweather.data.cache.models.Photo

interface PhotosContract {

    interface View {
        fun showNoResults()

        fun showLoading(isLoading: Boolean)

        fun showPhotos(photos: List<Photo>)

        fun getUserLocation()

        fun showError(msg: String)

        fun showInsertionSucceeded()
    }

    interface Presenter {
        fun newPhoto(uriPath: String)
    }
}