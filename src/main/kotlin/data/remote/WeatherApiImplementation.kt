package data.remote

import data.remote.dto.WeatherDto
import domain.model.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class KtorWeatherApiImplementation(private val client: HttpClient) : WeatherApi {
    private val json = Json { ignoreUnknownKeys = true }
    override suspend fun getCurrentWeather(cityName: String): Result<WeatherDto> {
        val apiKey = System.getenv(API_KEY)
        return try {
            val response = client.get(URL) {
                parameter(CITY_NAME_QUERY, cityName)
                parameter(API_KEY_QUERY, apiKey)
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    val weatherDto = json.decodeFromString<WeatherDto>(response.bodyAsText())
                    Result.success(weatherDto)
                }
                HttpStatusCode.BadRequest -> Result.failure(NetworkException())
                HttpStatusCode.Unauthorized -> Result.failure(InvalidApiKeyException())
                HttpStatusCode.NotFound -> Result.failure(WeatherNotFondException())
                else -> {
                    when (response.status.value) {
                        in 400..499 -> Result.failure(ClientErrorException())
                        in 500..599 -> Result.failure(ServerErrorException())
                        else -> Result.failure(Exception("$UNEXPECTED_ERROR ${response.status.value}"))
                    }
                }
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        const val URL = "https://api.openweathermap.org/data/2.5/weather"
        const val CITY_NAME_QUERY = "q"
        const val API_KEY_QUERY = "appid"
        const val API_KEY = "API_KEY"
        const val UNEXPECTED_ERROR = "Unexpected HTTP error:"
    }
}