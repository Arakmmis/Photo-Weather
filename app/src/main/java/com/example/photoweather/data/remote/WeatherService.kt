package com.example.photoweather.data.remote

import com.example.photoweather.data.remote.constants.WeatherServiceConstants
import com.example.photoweather.data.remote.models.Weather
import io.reactivex.Single

open class WeatherService {

    open fun getCurrentWeather(lat: Double, lon: Double): Single<Weather> {
        return WeatherServiceFactory().makeWeatherService()
            .getCurrentWeather(lat, lon)
            .flatMap {
                if (it.weatherInfo.isNotEmpty())
                    it.weatherInfo[0].weatherIcon =
                        WeatherServiceConstants.getWeatherIconUrl(it.weatherInfo[0].weatherIcon)

                Single.just(it)
            }
    }
}