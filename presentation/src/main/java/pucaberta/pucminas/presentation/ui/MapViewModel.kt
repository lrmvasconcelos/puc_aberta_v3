package pucaberta.pucminas.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import interactor.GetAllCommonLocationsInteractor
import pucaberta.pucminas.presentation.mapper.toMarkerOptionsList

class MapViewModel(
    private val mapInteractor: GetAllCommonLocationsInteractor
) : ViewModel() {

    private val _commonMarksObserver: MutableLiveData<List<MarkerOptions>> = MutableLiveData()
    val commonMarksObserver: LiveData<List<MarkerOptions>> get() = _commonMarksObserver

    val recepitivo = LatLng(-19.924542, -43.993056)

    fun loadCommonMarks() {
        _commonMarksObserver.value = mapInteractor.invoke().toMarkerOptionsList()
    }

}