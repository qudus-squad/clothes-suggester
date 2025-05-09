package data.remote.dto

import domain.model.CityWeather
import domain.model.WeatherType.Companion.fromWMO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    val id: Int,
    val main: Main,
    @SerialName("name")
    val cityName: String,
    @SerialName("weather")
    val weatherList: List<Weather>
)

fun WeatherDto.toDayCityWeather(): CityWeather {
    val weatherDescription = this.weatherList[0].description ?: "No description available"
    val weatherType = fromWMO(this.weatherList[0].icon ?: "")
    return CityWeather(
        temp = this.main.temperature,
        weatherDescription = weatherDescription,
        weatherType = weatherType,
        cityName = this.cityName
    )
}
