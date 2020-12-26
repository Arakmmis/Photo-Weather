package com.example.photoweather.photoslist

import com.example.photoweather.data.cache.models.Photo

interface PhotosContract {

    interface View {
        fun showLoading()

        fun hideLoading()

        fun showPhotos(photos: List<Photo>)

        fun getUserLocation()

        fun showError(msg: String)

        fun showInsertionSucceeded()
    }

    interface Presenter {
        fun getPhotos(): List<Photo>

        fun newPhoto(uriPath: String)

        fun getWeather(lat: Double, lon: Double)
    }
}