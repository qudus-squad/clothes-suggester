package domain.di

import domain.use_cases.ConvertKelvinToCelsiusUseCase
import domain.use_cases.GetClothesSuggestionUseCase
import domain.use_cases.ValidateCityDataUseCase
import org.koin.dsl.module

val domainModule = module {
    single { ConvertKelvinToCelsiusUseCase() }
    single { { ValidateCityDataUseCase() } }
    single {
        GetClothesSuggestionUseCase(
            get(),
            get(),
            get()
        )
    }

}