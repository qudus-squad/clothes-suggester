package domain.use_cases

import domain.repository.WeatherRepository

class GetClothesSuggestionUseCase(
    private val weatherRepository: WeatherRepository,
    private val convertKelvinToCelsiusUseCase: ConvertKelvinToCelsiusUseCase,
    private val validateCityDataUseCase: ValidateCityDataUseCase
) {
    suspend fun getClothesSuggestion(cityName: String): String? {
        return ""
    }

    companion object {
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