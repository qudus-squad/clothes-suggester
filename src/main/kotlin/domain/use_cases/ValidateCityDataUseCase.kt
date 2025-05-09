package domain.use_cases

import domain.model.InvalidCityNameException

class ValidateCityDataUseCase {
    fun isValidCityData(cityName: String): Boolean {
        if (!cityName.isValidCityName()) {
            throw InvalidCityNameException()
        }
        return true
    }

    private fun String.isValidCityName(): Boolean {
        val trimmedName = this.trim()
        return trimmedName.isNotEmpty() &&
                trimmedName.all { it.isLetter() || it == ' ' || it == '-' || it == '\'' || it == '.' }
    }
}