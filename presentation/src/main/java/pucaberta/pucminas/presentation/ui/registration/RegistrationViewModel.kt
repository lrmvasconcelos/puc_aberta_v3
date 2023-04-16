package pucaberta.pucminas.presentation.ui.registration

import androidx.lifecycle.ViewModel
import interactor.SetSessionInteractor

class RegistrationViewModel(
    private val setSessionInteractor: SetSessionInteractor
) : ViewModel() {

    fun setIsLogged(){
        setSessionInteractor()
    }
}