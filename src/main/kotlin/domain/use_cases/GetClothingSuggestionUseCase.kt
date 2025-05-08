package domain.use_cases

import domain.repository.WeatherRepository

class GetClothingSuggestionUseCase(
    private val weatherRepository: WeatherRepository
) {
}