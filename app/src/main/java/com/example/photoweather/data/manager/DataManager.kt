package com.example.photoweather.data.manager

import com.example.photoweather.data.cache.db.PhotoDao
import com.example.photoweather.data.cache.models.Photo
import com.example.photoweather.data.remote.WeatherService
import com.example.photoweather.data.remote.models.Weather
import io.reactivex.Single

class DataManager(private val photosDao: PhotoDao, private val weatherService: WeatherService)
    : IDataManager {

    override fun insertPhoto(photo: Photo): Single<Long> {
        return Single.fromCallable {
            photosDao.insert(photo)
        }
    }

    override fun getWeather(lat: Double, lon: Double): Single<Weather> {
        return weatherService.getCurrentWeather(lat, lon)
    }

    override fun getPhotos(): Single<List<Photo>> {
        return Single.fromCallable {
            photosDao.getAllPhotos()
        }
    }
}