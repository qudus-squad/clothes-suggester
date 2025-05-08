package domain.model


sealed class WeatherType(
    val weatherDesc: String,
) {
    object ClearSkyDay : WeatherType(
        weatherDesc = "Clear sky",
    )

    object ClearSkyNight : WeatherType(
        weatherDesc = "Clear sky",
    )

    object MainlyClear : WeatherType(
        weatherDesc = "Mainly clear",
    )
    object PartlyCloudyDay : WeatherType(
        weatherDesc = "Partly cloudy",
    )

    object PartlyCloudyNight : WeatherType(
        weatherDesc = "Partly cloudy",
    )

    object Overcast : WeatherType(
        weatherDesc = "Overcast",
    )
    object Foggy : WeatherType(
        weatherDesc = "Foggy",
    )

    object LightDrizzle : WeatherType(
        weatherDesc = "Light drizzle",
    )

    object SlightRain : WeatherType(
        weatherDesc = "Slight rain",
    )


    object SlightSnowFall: WeatherType(
        weatherDesc = "Slight snow fall",
    )

    object ModerateThunderstorm: WeatherType(
        weatherDesc = "Moderate thunderstorm",
    )


    companion object {
        fun fromWMO(code: String): WeatherType {
            return when(code) {
                "01d" -> ClearSkyDay
                "01n" -> ClearSkyNight
                "02d" -> PartlyCloudyDay
                "02n" -> PartlyCloudyNight
                "03d", "03n" -> MainlyClear
                "04d", "04n" -> Overcast
                "09d", "09n" -> LightDrizzle
                "10d" -> SlightRain
                "10n" -> SlightRain
                "11d", "11n" -> ModerateThunderstorm
                "13d", "13n" -> SlightSnowFall
                "50d", "50n" -> Foggy
                else -> ClearSkyDay
            }
        }
    }
}