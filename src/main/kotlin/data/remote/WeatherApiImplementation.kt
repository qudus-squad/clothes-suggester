package data.remote

import data.remote.dto.WeatherDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json.Default.decodeFromString

class KtorWeatherApiImplementation(private val client: HttpClient) : WeatherApi {
    override suspend fun getCurrentWeather(city: String): Result<WeatherDto> {
        val apiKey = System.getenv(API_KEY)
        return try {
            val response: String = client.get(URL) {
                parameter(CITY_NAME_QUERY, city)
                parameter(API_KEY_QUERY, apiKey)
            }.bodyAsText()
            val weatherDto = decodeFromString<WeatherDto>(response)
            Result.success(weatherDto)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        const val URL = "https://api.openweathermap.org/data/2.5/weather"
        const val CITY_NAME_QUERY = "q"
        const val API_KEY_QUERY = "appid"
        const val API_KEY = "API_KEY"
    }
}