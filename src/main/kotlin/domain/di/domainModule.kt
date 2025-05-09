package domain.di

import domain.use_cases.ConvertKelvinToCelsiusUseCase
import domain.use_cases.GetClothesSuggestionUseCase
import org.koin.dsl.module

val domainModule = module {
    single { ConvertKelvinToCelsiusUseCase() }
    single { GetClothesSuggestionUseCase(get(), get()) }

}