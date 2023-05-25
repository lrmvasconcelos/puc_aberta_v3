package pucaberta.pucminas.presentation.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import models.MarkLocation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pucaberta.pucminas.core.*
import pucaberta.pucminas.core.PermissionUtils.PermissionDeniedDialog.Companion.newInstance
import pucaberta.pucminas.core.PermissionUtils.isGPSEnabled
import pucaberta.pucminas.core.PermissionUtils.isPermissionGranted
import pucaberta.pucminas.core.event.BottomSheetFinishEvent
import pucaberta.pucminas.presentation.R
import pucaberta.pucminas.presentation.databinding.MapActivityBinding
import pucaberta.pucminas.presentation.mapper.toMarkerOptionsList
import pucaberta.pucminas.presentation.ui.bottomsheet.QrCodeBottomSheetDialog
import pucaberta.pucminas.presentation.ui.map.adapter.CustomInfoWindowAdapter

class MapActivity : AppCompatActivity(),
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

    private var permissionDenied = false
    private lateinit var map: GoogleMap

    private val viewModel: MapViewModel by viewModel()

    private val binding: MapActivityBinding by viewBinding(
        MapActivityBinding::inflate
    )

    private val finishEvent: BottomSheetFinishEvent by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide();
        }
        setContentView(binding.root)
        setupComponents()
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        setupObservers()
        viewModel.getUserScore()
    }

    private fun setupComponents() {
        with(binding) {
            scoreSeekBar.isTouchListenerEnabled = false
            qrCodeButton.clickWithDebounce {
                openQrBottomSheet()
            }
        }
    }

    private fun setupObservers() = with(viewModel) {
        observe(allMarks) {
            setupMarkers(it)
        }

        observe(openQrCodeBottomSheet) {
            openQrBottomSheet()
        }

        observe(userLevel) {
            setUserLevel(it)
        }

        observeEvent(finishEvent.finishFlow) {
        }
    }

    private fun setupMarkers(markers: List<MarkLocation>) {
        map.setInfoWindowAdapter(
            CustomInfoWindowAdapter(
                context = this@MapActivity,
                markers = markers
            )
        )

        markers.toMarkerOptionsList().forEach {
            map.addMarker(it)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap.apply {
            uiSettings.isCompassEnabled = false
            mapType = GoogleMap.MAP_TYPE_HYBRID
            isBuildingsEnabled = true
            isIndoorEnabled = true
            uiSettings.isZoomControlsEnabled = false
            setOnMyLocationButtonClickListener(this@MapActivity)
            setOnMyLocationClickListener(this@MapActivity)
            setOnInfoWindowClickListener {
                viewModel.onMarkerClick(it)
            }
            moveCamera(CameraUpdateFactory.newCameraPosition(getCameraPosition(this)))
        }
        enableMyLocation()
        viewModel.loadAllMarks()
    }

    private fun getCameraPosition(googleMap: GoogleMap) = CameraPosition
        .builder(googleMap.cameraPosition)
        .target(viewModel.reception)
        .bearing(310.toFloat())
        .zoom(17.toFloat())
        .build()

    override fun onMyLocationButtonClick(): Boolean {
        if (!isGPSEnabled(this)) {
            Toast.makeText(
                this,
                this.resources.getString(R.string.gps_disable_message),
                Toast.LENGTH_SHORT
            ).show()
        }
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        PermissionUtils.enableMyLocation(
            activity = this
        ) {
            map.isMyLocationEnabled = true
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError()
            permissionDenied = false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != PermissionUtils.LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
            return
        }

        if (isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation()
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
            // [END_EXCLUDE]
        }
    }

    private fun showMissingPermissionError() {
        newInstance(true).show(supportFragmentManager, "dialog")
    }

    private fun openQrBottomSheet() {
        QrCodeBottomSheetDialog.newInstance().showBottomSheet(
            this.supportFragmentManager,
            QrCodeBottomSheetDialog::class.java.name
        )
    }

    private fun setUserLevel(level: Int) {
        with(binding) {
            actvScore.text = level.toString()
            scoreSeekBar.currentValue = level
        }
    }

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }
    }

}