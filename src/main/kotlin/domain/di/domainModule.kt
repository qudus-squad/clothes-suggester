package domain.di

import domain.use_cases.GetClothingSuggestionUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetClothingSuggestionUseCase(get()) }

}