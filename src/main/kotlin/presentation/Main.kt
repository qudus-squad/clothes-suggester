package presentation

import data.di.dataModule
import domain.di.domainModule
import domain.repository.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

fun main() {
    startKoin {
        modules(dataModule, domainModule)
    }
    val repository: WeatherRepository = getKoin().get()
    runBlocking {
        val deferred = async {
            repository.getCurrentWeather("cairo")
        }

        val weather = deferred.await()

        print(weather.temp)
    }


}