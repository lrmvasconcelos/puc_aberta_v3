package pucaberta.pucminas.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pucaberta.pucminas.presentation.ui.map.MapViewModel

val presentationModule = module {

    viewModel { MapViewModel(get(), get()) }

}