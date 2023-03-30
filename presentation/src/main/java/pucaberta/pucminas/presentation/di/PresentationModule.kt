package pucaberta.pucminas.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pucaberta.pucminas.presentation.ui.map.MapViewModel
import pucaberta.pucminas.presentation.ui.registration.RegistrationViewModel

val presentationModule = module {

    viewModel { MapViewModel(get(), get()) }

    viewModel { RegistrationViewModel() }

}