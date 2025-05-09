package domain.use_cases

import domain.model.CityWeather
import domain.model.ExceptionsMessages.WEATHER_NOT_FOUND
import domain.model.WeatherNotFondException
import domain.model.WeatherType
import domain.repository.WeatherRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetClothesSuggestionUseCaseTest {
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var convertKelvinToCelsiusUseCase: ConvertKelvinToCelsiusUseCase
    private lateinit var validateCityDataUseCase: ValidateCityDataUseCase
    private lateinit var getClothesSuggestionUseCase: GetClothesSuggestionUseCase

    @BeforeEach
    fun setup() {
        weatherRepository = mockk(relaxed = true)
        convertKelvinToCelsiusUseCase = ConvertKelvinToCelsiusUseCase()
        validateCityDataUseCase = ValidateCityDataUseCase()
        getClothesSuggestionUseCase =
            GetClothesSuggestionUseCase(weatherRepository, convertKelvinToCelsiusUseCase, validateCityDataUseCase)
    }


    @Test
    fun `should return heavy winter clothing suggestion when temperature is below 0`() = runTest {
        // Given
        val cityName = "Oslo"
        val cityWeather = CityWeather(
            cityName = cityName,
            temp = 270.0,
            weatherType = WeatherType.SlightSnowFall,
            weatherDescription = "light snow"
        )

        coEvery { weatherRepository.getCurrentWeather(cityName) } returns Result.success(cityWeather)

        // When
        val result = getClothesSuggestionUseCase.getClothesSuggestion(cityName)

        // Then
        result.shouldContain("Heavy winter coat, scarf, gloves, and thermal layers")
    }

    @Test
    fun `should return light jacket suggestion when temperature is between 10 and 20`() = runTest {
        // Given
        val cityName = "London"
        val cityWeather = CityWeather(
            cityName = cityName,
            temp = 290.0,
            weatherType = WeatherType.SlightRain,
            weatherDescription = "light rain"
        )

        coEvery { weatherRepository.getCurrentWeather(cityName) } returns Result.success(cityWeather)

        // When
        val result = getClothesSuggestionUseCase.getClothesSuggestion(cityName)

        // Then
        result.shouldContain("Waterproof jacket and umbrella")
    }

    @Test
    fun `should return light clothing suggestion when temperature is above 20`() = runTest {
        // Given
        val cityName = "Barcelona"
        val cityWeather = CityWeather(
            cityName = cityName,
            temp = 303.0,
            weatherType = WeatherType.ClearSky,
            weatherDescription = "clear sky"
        )

        coEvery { weatherRepository.getCurrentWeather(cityName) } returns Result.success(cityWeather)

        // When
        val result = getClothesSuggestionUseCase.getClothesSuggestion(cityName)

        // Then
        result.shouldContain("T-shirt, shorts or light pants")
    }


    @Test
    fun `should return -The weather for this city cannot be determined- message when weather repository fails`() =
        runTest {
            // Given
            val cityName = "KafrEldawar"

            coEvery {
                weatherRepository.getCurrentWeather(cityName)
            } returns Result.failure(WeatherNotFondException())

            // When
            val result = getClothesSuggestionUseCase.getClothesSuggestion(cityName)

            // Then
            result shouldBe WEATHER_NOT_FOUND
        }

    @Test
    fun `should throw return City name is not valid when city name is empty`() = runTest {
        // Given
        val invalidCityName = ""

        // When
        val result = getClothesSuggestionUseCase.getClothesSuggestion(invalidCityName)

        // Then
        result shouldBe INVALID_CITY_NAME
    }

    @Test
    fun `should throw return City name is not valid when city name is blank`() = runTest {
        // Given
        val invalidCityName = " "

        // When
        val result = getClothesSuggestionUseCase.getClothesSuggestion(invalidCityName)

        // Then
        result shouldBe INVALID_CITY_NAME

    }

    @Test
    fun `should return City name is not valid when city name has not letter char`() = runTest {
        // Given
        val invalidCityName = "123Helwan"

        // When
        val result = getClothesSuggestionUseCase.getClothesSuggestion(invalidCityName)

        // Then
        result shouldBe INVALID_CITY_NAME
    }

    companion object {
        const val INVALID_CITY_NAME = "City name is not valid"

    }
}
