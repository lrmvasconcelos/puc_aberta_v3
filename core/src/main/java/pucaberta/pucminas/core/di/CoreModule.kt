package pucaberta.pucminas.core.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import pucaberta.pucminas.core.event.BottomSheetFinishEvent
import pucaberta.pucminas.core.providers.ResourceRawProvider
import pucaberta.pucminas.core.providers.ResourceRawProviderImpl


val coreModule = module {
    factory<ResourceRawProvider> { ResourceRawProviderImpl(get()) }

    factory { CoroutineScope(Dispatchers.IO) }

    single { BottomSheetFinishEvent(get()) }
}