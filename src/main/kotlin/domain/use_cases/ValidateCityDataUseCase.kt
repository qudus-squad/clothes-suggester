package domain.use_cases

import domain.model.InvalidCityNameException

class ValidateCityDataUseCase {
  
    fun validateCityData(cityName: String): Boolean {
        if (!cityName.isValidCityName()) {
            throw InvalidCityNameException()
        }
        return true
    }

    private fun isValidCityName(cityName: String): Boolean {
        val trimmedName = cityName.trim()
        return trimmedName.isNotEmpty() &&
                trimmedName.all { it.isLetter() || it == ' ' || it == '-' || it == '\'' || it == '.' }
    }
}