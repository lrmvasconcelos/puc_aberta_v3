package di

import interactor.GetAllCommonLocationsInteractor
import org.koin.dsl.module

val domainModule = module{
    factory { GetAllCommonLocationsInteractor(get()) }
}