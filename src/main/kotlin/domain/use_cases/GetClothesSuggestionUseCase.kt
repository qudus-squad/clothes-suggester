package domain.use_cases

import domain.model.CityWeather
import domain.model.InvalidCityNameException
import domain.model.WeatherType
import domain.repository.WeatherRepository

class GetClothesSuggestionUseCase(
    private val weatherRepository: WeatherRepository,
    private val convertKelvinToCelsiusUseCase: ConvertKelvinToCelsiusUseCase,
    private val validateCityDataUseCase: ValidateCityDataUseCase
) {
    lateinit var cityWeather: CityWeather
    suspend fun getClothesSuggestion(cityName: String): String {
        return try {
            validateCityDataUseCase.validateCityData(cityName)
            val result = weatherRepository.getCurrentWeather(cityName)
            result.fold(
                onSuccess = { cityWeather = it },
                onFailure = {
                    return it.message ?: UNKNOWN_ERROR_CHECK_YOUR_INTERNET
                }
            )
            val temperature = getCelsiusTemperature(cityWeather.temp)

            val stringBuilder = StringBuilder()
            stringBuilder.append(String.format(WEATHER_SUGGESTION_HEADER, cityName))
            stringBuilder.append(String.format(TEMPERATURE_INFO, temperature))
            stringBuilder.append(String.format(WEATHER_CONDITION, cityWeather.weatherType.weatherDesc))

            when {
                temperature < 0 -> {
                    stringBuilder.append(HEAVY_WINTER_CLOTHING)
                    stringBuilder.append(WARM_HAT_AND_BOOTS)
                }

                temperature in 0.0..10.0 -> {
                    stringBuilder.append(WARM_JACKET_CLOTHING)
                    stringBuilder.append(HAT_AND_GLOVES_OPTION)
                }

                temperature in 10.1..20.0 -> {
                    stringBuilder.append(LIGHT_JACKET_CLOTHING)
                }

                temperature > 20 -> {
                    stringBuilder.append(LIGHT_CLOTHING)
                    stringBuilder.append(SUN_PROTECTION)
                }
            }

            when (cityWeather.weatherType) {
                WeatherType.SlightRain, WeatherType.LightDrizzle, WeatherType.ModerateThunderstorm -> {
                    stringBuilder.append(WATERPROOF_CLOTHING)
                }

                WeatherType.SlightSnowFall -> {
                    stringBuilder.append(SNOW_CLOTHING)
                }

                WeatherType.Foggy -> {
                    stringBuilder.append(HIGH_VISIBILITY_CLOTHING)
                }

                else -> {}
            }

            if (cityWeather.weatherType == WeatherType.ClearSky) {
                stringBuilder.append(NIGHT_VISIBILITY_CLOTHING)
            }

            stringBuilder.toString()
        } catch (e: InvalidCityNameException) {
            e.message ?: UNKNOWN_ERROR_CHECK_YOUR_INTERNET
        } catch (e: Exception) {
            e.message ?: UNKNOWN_ERROR_CHECK_YOUR_INTERNET
        }
    }

    private fun getCelsiusTemperature(kelvinTemperature: Double): Double {
        return convertKelvinToCelsiusUseCase.kelvinToCelsius(kelvinTemperature)
    }

    companion object {
        const val UNKNOWN_ERROR_CHECK_YOUR_INTERNET = "Unknow error has occurred, check your internet connection "
        const val WEATHER_SUGGESTION_HEADER = "Weather-based clothing suggestion for %s:\n"
        const val TEMPERATURE_INFO = "Temperature: %.2f°C\n"
        const val WEATHER_CONDITION = "Weather condition: %s\n"
        const val HEAVY_WINTER_CLOTHING = "- Heavy winter coat, scarf, gloves, and thermal layers\n"
        const val WARM_HAT_AND_BOOTS = "- Warm hat and insulated boots\n"
        const val WARM_JACKET_CLOTHING = "- Warm jacket, sweater, and long pants\n"
        const val HAT_AND_GLOVES_OPTION = "- Consider a hat and gloves\n"
        const val LIGHT_JACKET_CLOTHING = "- Light jacket or sweater, long-sleeve shirt, and pants\n"
        const val LIGHT_CLOTHING = "- T-shirt, shorts or light pants\n"
        const val SUN_PROTECTION = "- Sunglasses and sunscreen if sunny\n"
        const val WATERPROOF_CLOTHING = "- Waterproof jacket and umbrella\n"
        const val SNOW_CLOTHING = "- Waterproof jacket, snow boots, and warm socks\n"
        const val HIGH_VISIBILITY_CLOTHING = "- High-visibility clothing for safety\n"
        const val NIGHT_VISIBILITY_CLOTHING = "- Consider reflective or light-colored clothing for visibility\n"
    }
}
