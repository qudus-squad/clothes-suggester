package domain.repository

import domain.model.CityWeather

interface WeatherRepository {
    suspend fun getCurrentWeather(cityName: String): Result<CityWeather>
}