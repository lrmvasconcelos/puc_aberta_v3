package pucaberta.pucminas.presentation.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import interactor.GetAllCommonLocationsInteractor
import interactor.GetIceiLocationInteractor
import models.MarkLocation
import pucaberta.pucminas.presentation.mapper.toMarkerOptionsList
import utils.RECEPTION_LOCATION

class MapViewModel(
    private val mapInteractor: GetAllCommonLocationsInteractor,
    private val iceiInteractor: GetIceiLocationInteractor
) : ViewModel() {

    private val _commonMarksObserver: MutableLiveData<List<MarkLocation>> = MutableLiveData()
    val commonMarksObserver: LiveData<List<MarkLocation>> get() = _commonMarksObserver

    private val _iceiMarksObserver: MutableLiveData<List<MarkLocation>> = MutableLiveData()
    val iceiMarksObserver: LiveData<List<MarkLocation>> get() = _iceiMarksObserver

    private val _allMarks: MutableLiveData<List<MarkLocation>> = MutableLiveData()
    val allMarks: LiveData<List<MarkLocation>> get() = _allMarks

    val reception = RECEPTION_LOCATION.run {
        LatLng(this.latitude, this.longitude)
    }

    fun loadAllMarks() {
        _allMarks.value = mutableListOf<MarkLocation>().apply {
            addAll(mapInteractor())
            addAll(iceiInteractor())
        }
    }

    fun loadCommonMarks() {
        _commonMarksObserver.value = mapInteractor()
    }

    fun loadICEIMarks() {
        _iceiMarksObserver.value = iceiInteractor()
    }

}