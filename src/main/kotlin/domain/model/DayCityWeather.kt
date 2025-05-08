package domain.model


data class DayCityWeather(
    val cityName: String,
    val temp: Double,
    val weatherDescription: String,
    val weatherType: WeatherType
)