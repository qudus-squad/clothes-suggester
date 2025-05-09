package domain.repository

import domain.model.DayCityWeather

interface WeatherRepository {
    suspend fun getCurrentWeather(cityName: String): DayCityWeather
}