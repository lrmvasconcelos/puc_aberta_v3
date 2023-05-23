package pucaberta.pucminas.presentation.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import interactor.GetAllLocationsInteractor
import interactor.GetCommonLocationsInteractor
import interactor.GetIceiLocationInteractor
import models.MarkLocation
import utils.RECEPTION_LOCATION

class MapViewModel(
    private val mapInteractor: GetCommonLocationsInteractor,
    private val iceiInteractor: GetIceiLocationInteractor,
    private val getAllLocationsInteractor: GetAllLocationsInteractor
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
        _allMarks.value = getAllLocationsInteractor()
    }

    fun loadCommonMarks() {
        _commonMarksObserver.value = mapInteractor()
    }

    fun loadICEIMarks() {
        _iceiMarksObserver.value = iceiInteractor()
    }

}