package di

import interactor.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetCommonLocationsInteractor(get()) }
    factory { GetIceiLocationInteractor(get()) }
    factory { GetSessionInteractor(get()) }
    factory { SetSessionInteractor(get()) }
    factory { SaveUsersLocationInteractor(get()) }
    factory { GetAllLocationsInteractor(get()) }
    factory { GetUserScoreInteractor(get()) }
    factory { SetUserScoreInteractor(get()) }
    factory { UpdateCommonLocationsInteractor(get()) }
    factory { UpdateIceiLocationsInteractor(get()) }
}