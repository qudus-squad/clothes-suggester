package data

import data.remote.WeatherApi
import domain.repository.WeatherRepository

class WeatherRepositoryImplementation(
    private val weatherApi: WeatherApi
) : WeatherRepository {}