package com.example.photoweather.testutils

import com.example.photoweather.data.remote.WeatherService
import com.example.photoweather.data.remote.models.Weather
import io.reactivex.Single

class MockWeatherService: WeatherService() {

    override fun getCurrentWeather(cityName: String): Single<Weather> {
        return Single.just(DataUtils.createWeather(1)[0])
    }
}