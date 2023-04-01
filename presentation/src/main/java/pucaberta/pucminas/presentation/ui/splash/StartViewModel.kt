package pucaberta.pucminas.presentation.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import interactor.GetSessionInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartViewModel(
    private val getSessionInteractor: GetSessionInteractor
) : ViewModel() {

    private val _isLogged: MutableLiveData<Boolean> = MutableLiveData()
    val isLogged: LiveData<Boolean> get() = _isLogged

    fun starDelay() = viewModelScope.launch (Dispatchers.Main.immediate){
        delay(3000)
        _isLogged.value = getSessionInteractor()
    }

}