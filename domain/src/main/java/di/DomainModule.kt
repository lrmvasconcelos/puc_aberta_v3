package di

import interactor.GetAllCommonLocationsInteractor
import interactor.GetIceiLocationInteractor
import interactor.GetSessionInteractor
import interactor.SetSessionInteractor
import org.koin.dsl.module

val domainModule = module{
    factory { GetAllCommonLocationsInteractor(get()) }
    factory { GetIceiLocationInteractor(get()) }
    factory { GetSessionInteractor(get()) }
    factory { SetSessionInteractor(get()) }
}