package pucaberta.pucminas.presentation.ui.registration

import androidx.lifecycle.ViewModel
import interactor.SaveUsersLocationInteractor
import interactor.SetSessionInteractor

class RegistrationViewModel(
    private val setSessionInteractor: SetSessionInteractor,
    private val saveUserLocationInteractor: SaveUsersLocationInteractor
) : ViewModel() {

    fun setIsLogged() {
        setSessionInteractor()
        saveUserLocationInteractor()
    }
}