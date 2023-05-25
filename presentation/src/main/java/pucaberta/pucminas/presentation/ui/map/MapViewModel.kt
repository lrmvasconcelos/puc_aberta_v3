package pucaberta.pucminas.presentation.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import interactor.*
import models.LocationType
import models.MarkLocation
import utils.RECEPTION_LOCATION

class MapViewModel(
    private val commonLocationsInteractor: GetCommonLocationsInteractor,
    private val iceiInteractor: GetIceiLocationInteractor,
    private val getAllLocationsInteractor: GetAllLocationsInteractor,
    private val getUserScoreInteractor: GetUserScoreInteractor,
    private val setUserScoreInteractor: SetUserScoreInteractor

) : ViewModel() {

    private val _allMarks: MutableLiveData<List<MarkLocation>> = MutableLiveData()
    val allMarks: LiveData<List<MarkLocation>> get() = _allMarks

    private val _openQrCodeBottomSheet: MutableLiveData<Float> = MutableLiveData()
    val openQrCodeBottomSheet: LiveData<Float> get() = _openQrCodeBottomSheet

    private val _userLevel: MutableLiveData<Int> = MutableLiveData()
    val userLevel: LiveData<Int> get() = _userLevel

    var isAnimationEnabled = false

    val reception = RECEPTION_LOCATION.run {
        LatLng(this.latitude, this.longitude)
    }

    fun loadAllMarks() {
        _allMarks.value = getAllLocationsInteractor()
    }

    fun onMarkerClick(marker: Marker) {
        _openQrCodeBottomSheet.value = marker.zIndex
    }

    fun getUserScore() {
        _userLevel.value = getUserScoreInteractor().size
    }

    fun processQrCodeResult(result: String) {
        val markers = getUserScoreInteractor().toMutableList()

        val markerType = markers.find { it == result }

        if (markerType.isNullOrBlank() && validateMarkType(result)) {
            markers.add(result)
            setUserScoreInteractor(markers)
            resetMapsMarkers(result)
        } else {
            Log.d("ERROR", "QR Já Scaneado ou Inválido")
        }
    }

    private fun validateMarkType(result: String): Boolean {
        LocationType.values().forEach {
            if (it.name == result) return true
        }

        return false
    }

    private fun resetMapsMarkers(markerType: String) {

        when (val locationType = LocationType.valueOf(markerType)) {
            LocationType.INSTITUTOS, LocationType.AUDITORIOS -> {
                updateIceiLocation(locationType)
            }
            LocationType.RECEPTIVO, LocationType.FEIRA_CURSOS -> {
                updateCommonsLocation(locationType)
            }
        }

        isAnimationEnabled = true
        getUserScore()

    }

    private fun updateIceiLocation(locationType: LocationType) {
        val iceiLocations = iceiInteractor()

        iceiLocations.forEachIndexed { position, item ->
            if (item.locationType == locationType) {
                iceiLocations[position].showQrCode = false
            }
        }

        Log.d("Teste", "Teste")

    }

    private fun updateCommonsLocation(locationType: LocationType) {
        val commonLocation = commonLocationsInteractor()

        commonLocation.forEachIndexed { position, item ->
            if (item.locationType == locationType) {
                commonLocation[position].showQrCode = false
            }
        }

        Log.d("Teste", "Teste")
    }
}