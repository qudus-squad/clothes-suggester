package domain.use_cases

import domain.model.CityWeather
import domain.model.InvalidCityNameException
import domain.model.WeatherNotFondException
import domain.model.WeatherType
import domain.repository.WeatherRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
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
        val cityName = "moscow"
        val cityWeather = CityWeather(
            cityName = cityName,
            temp = 270.0,
            weatherType = WeatherType.SlightSnowFall,
            weatherDescription = "Cold"
        )

        coEvery { weatherRepository.getCurrentWeather(cityName) } returns Result.success(cityWeather)

        // When
        val result = getClothesSuggestionUseCase.getClothesSuggestion(cityName)

        // Then
        result shouldBe """
            Weather-based clothing suggestion for Oslo:
            Temperature: -3.00°C
            Weather condition: Slight snow fall
            - Heavy winter coat, scarf, gloves, and thermal layers
            - Warm hat and insulated boots
            - Waterproof jacket, snow boots, and warm socks
        """.trimIndent()
    }

    @Test
    fun `should return error message when weather repository fails`() = runTest {
        // Given
        val cityName = "KafrEldawar"
        val errorMessage = "City not found"

        coEvery {
            weatherRepository.getCurrentWeather(cityName)
        } returns Result.failure(WeatherNotFondException())

        // When
        val result = getClothesSuggestionUseCase.getClothesSuggestion(cityName)

        // Then
        result shouldBe errorMessage
    }

    @Test
    fun `should throw InvalidCityNameException when city name is empty`() = runTest {
        // Given
        val invalidCityName = ""

        // When && Then
        shouldThrow<InvalidCityNameException> {
            getClothesSuggestionUseCase.getClothesSuggestion(invalidCityName)
        }
    }

    @Test
    fun `should throw InvalidCityNameException when city name is blank`() = runTest {
        // Given
        val invalidCityName = " "

        // When && Then
        shouldThrow<InvalidCityNameException> {
            getClothesSuggestionUseCase.getClothesSuggestion(invalidCityName)
        }
    }

    @Test
    fun `should throw InvalidCityNameException when city name has not letter char`() = runTest {
        // Given
        val invalidCityName = "123Helwan"

        // When && Then
        shouldThrow<InvalidCityNameException> {
            getClothesSuggestionUseCase.getClothesSuggestion(invalidCityName)
        }
    }
}