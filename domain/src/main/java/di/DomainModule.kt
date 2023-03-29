package di

import interactor.GetAllCommonLocationsInteractor
import interactor.GetIceiLocationInteractor
import org.koin.dsl.module

val domainModule = module{
    factory { GetAllCommonLocationsInteractor(get()) }
    factory { GetIceiLocationInteractor(get()) }
}