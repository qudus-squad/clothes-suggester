package presentation

import data.di.dataModule
import domain.di.domainModule
import domain.use_cases.GetClothesSuggestionUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

fun main() {
    startKoin {
        modules(dataModule, domainModule)
    }
    val getClothesSuggestionUseCase: GetClothesSuggestionUseCase = getKoin().get()
    print("Enter your City Name: ")
    val cityName = readln()
    runBlocking {
        try {
            val deferred = async {
                getClothesSuggestionUseCase.getClothesSuggestion(cityName)
            }

            val suggestion = deferred.await()
            println(suggestion)

        } catch (e: Exception) {
            println(e.message)
        }
    }
}