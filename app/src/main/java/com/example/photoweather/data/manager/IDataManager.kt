package com.example.photoweather.data.manager

import com.example.photoweather.data.cache.models.Photo
import com.example.photoweather.data.remote.models.Weather
import io.reactivex.Single

interface IDataManager {

    fun insertPhoto(photo: Photo): Single<Long>

    fun getWeather(cityName: String): Single<Weather>

    fun getPhotos(): Single<List<Photo>>
}