package pucaberta.pucminas.presentation.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.MarkerOptions
import interactor.GetAllCommonLocationsRepository

class MapViewModel(
    private val mapInteractor: GetAllCommonLocationsRepository
) : ViewModel() {

    private val _commonMarksObserver: MutableLiveData<List<MarkerOptions>> = MutableLiveData()
    val commonMarksObserver: LiveData<List<MarkerOptions>> get() = _commonMarksObserver

    fun loadCommonMarks(){

    }

}