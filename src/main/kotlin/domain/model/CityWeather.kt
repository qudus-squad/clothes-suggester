package domain.model


data class CityWeather(
    val cityName: String,
    val temp: Double,
    val weatherDescription: String,
    val weatherType: WeatherType
)