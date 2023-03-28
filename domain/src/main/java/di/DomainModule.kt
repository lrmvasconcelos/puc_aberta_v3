package di

import interactor.GetAllCommonLocationsRepository
import org.koin.dsl.module

val domainModule = module{
    factory { GetAllCommonLocationsRepository(get()) }
}