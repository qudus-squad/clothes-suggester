package data.di

import data.remote.KtorWeatherApiImplementation
import data.remote.WeatherApi
import data.repository.WeatherRepositoryImplementation
import domain.repository.WeatherRepository
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import org.koin.dsl.module

val dataModule = module {
    single<HttpClient> { HttpClient(CIO) }
    single<WeatherApi> { KtorWeatherApiImplementation(get()) }
    single<WeatherRepository> { WeatherRepositoryImplementation(get()) }
}