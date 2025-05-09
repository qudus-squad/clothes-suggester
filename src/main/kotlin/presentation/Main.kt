package presentation

import data.di.dataModule
import domain.di.domainModule
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(dataModule, domainModule)
    }
}