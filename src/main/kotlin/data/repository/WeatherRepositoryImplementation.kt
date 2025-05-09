package data.repository

import data.remote.WeatherApi
import data.remote.dto.toDayCityWeather
import domain.model.CityWeather
import domain.repository.WeatherRepository

class WeatherRepositoryImplementation(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getCurrentWeather(cityName: String): Result<CityWeather> {
        val result = weatherApi.getCurrentWeather(cityName)
        return result.fold(
            onSuccess = {
                Result.success(it.toDayCityWeather())
            },
            onFailure = {
                Result.failure<CityWeather>(it)
            }
        )
    }
}