package di

import interactor.*
import org.koin.dsl.module

val domainModule = module{
    factory { GetAllCommonLocationsInteractor(get()) }
    factory { GetIceiLocationInteractor(get()) }
    factory { GetSessionInteractor(get()) }
    factory { SetSessionInteractor(get()) }
    factory { SaveUsersLocationInteractor(get()) }
}