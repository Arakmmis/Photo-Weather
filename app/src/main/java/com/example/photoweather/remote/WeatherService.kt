package com.example.photoweather.remote

import com.example.photoweather.remote.constants.WeatherServiceConstants
import com.example.photoweather.remote.models.Weather
import io.reactivex.Single

class WeatherService {

    fun getCurrentWeather(cityName: String): Single<Weather> {
        return WeatherServiceFactory().makeWeatherService()
            .getCurrentWeather(cityName)
            .flatMap {
                if (it.weatherInfo.isNotEmpty())
                    it.weatherInfo[0].weatherIcon =
                        WeatherServiceConstants.getWeatherIconUrl(it.weatherInfo[0].weatherIcon)

                Single.just(it)
            }
    }
}