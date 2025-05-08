package domain.use_cases

class ConvertKelvinToCelsiusUseCase {
    fun kelvinToCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }
}