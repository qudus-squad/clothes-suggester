package data.repository

import data.remote.WeatherApi
import data.remote.dto.toDayCityWeather
import domain.model.DayCityWeather
import domain.model.WeatherNotFondException
import domain.repository.WeatherRepository

class WeatherRepositoryImplementation(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getCurrentWeather(cityName: String): DayCityWeather {
        val result = weatherApi.getCurrentWeather(cityName)
        var cityWeather: DayCityWeather? = null
        result.fold(
            onSuccess = {
                cityWeather = it.toDayCityWeather()
            },
            onFailure = {
                throw it
            }
        )
        return cityWeather ?: throw WeatherNotFondException()
    }
}