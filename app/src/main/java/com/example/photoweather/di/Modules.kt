package com.example.photoweather.di

import com.example.photoweather.data.cache.db.PhotosDatabase
import com.example.photoweather.data.manager.DataManager
import com.example.photoweather.data.remote.WeatherService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {
    factory {
        DataManager(
            PhotosDatabase.getDatabase(androidContext()).getPhotoDao(),
            WeatherService()
        )
    }
}