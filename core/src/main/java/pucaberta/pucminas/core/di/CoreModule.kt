package pucaberta.pucminas.core.di

import org.koin.dsl.module
import pucaberta.pucminas.core.providers.ResourceRawProvider
import pucaberta.pucminas.core.providers.ResourceRawProviderImpl


val coreModule = module {
    factory<ResourceRawProvider> { ResourceRawProviderImpl(get()) }
}