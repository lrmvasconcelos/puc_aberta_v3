package pucaberta.pucminas.presentation.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import interactor.GetAllCommonLocationsInteractor
import interactor.GetIceiLocationInteractor
import pucaberta.pucminas.presentation.mapper.toMarkerOptionsList
import utils.RECEPTION_LOCATION

class MapViewModel(
    private val mapInteractor: GetAllCommonLocationsInteractor,
    private val iceiInteractor: GetIceiLocationInteractor
) : ViewModel() {

    private val _commonMarksObserver: MutableLiveData<List<MarkerOptions>> = MutableLiveData()
    val commonMarksObserver: LiveData<List<MarkerOptions>> get() = _commonMarksObserver

    private val _iceiMarksObserver: MutableLiveData<List<MarkerOptions>> = MutableLiveData()
    val iceiMarksObserver: LiveData<List<MarkerOptions>> get() = _iceiMarksObserver

    val reception = RECEPTION_LOCATION.run {
        LatLng(this.latitude, this.longitude)
    }

    fun loadCommonMarks() {
        _commonMarksObserver.value = mapInteractor.invoke().toMarkerOptionsList()
    }

    fun loadICEIMarks() {
        _iceiMarksObserver.value = iceiInteractor().toMarkerOptionsList()
    }

}