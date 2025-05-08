package data.di

import data.WeatherRepositoryImplementation
import data.remote.KtorWeatherApiImplementation
import data.remote.WeatherApi
import domain.repository.WeatherRepository
import domain.use_cases.GetClothingSuggestionUseCase
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import org.koin.dsl.module

val appModule = module {
    single<HttpClient> { HttpClient(CIO) }
    single<WeatherApi> { KtorWeatherApiImplementation(get()) }
    single<WeatherRepository> { WeatherRepositoryImplementation(get()) }
    single { GetClothingSuggestionUseCase(get()) }
}