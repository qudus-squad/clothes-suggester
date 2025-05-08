package data.remote

import data.remote.dto.WeatherDto

interface WeatherApi {
    suspend fun getCurrentWeather(cityName: String): Result<WeatherDto>
}