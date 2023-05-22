package di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import repoInterfaces.MapRepository
import repoInterfaces.SessionRepository
import repository.MapRepositoryImpl
import repository.SessionRepositoryImpl

val dataModule = module {

    factory<MapRepository> { MapRepositoryImpl(get()) }

    factory<SessionRepository> { SessionRepositoryImpl(androidContext()) }
}