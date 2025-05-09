package data.remote

import data.remote.dto.WeatherDto

interface WeatherApi {
    suspend fun getCurrentWeather(city: String): Result<WeatherDto>
}