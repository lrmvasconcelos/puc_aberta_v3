package di

import org.koin.dsl.module
import repoInterfaces.MapRepository
import repository.MapRepositoryImpl

val dataModule = module{

    factory<MapRepository> { MapRepositoryImpl() }
}