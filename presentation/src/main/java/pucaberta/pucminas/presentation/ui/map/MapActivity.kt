package pucaberta.pucminas.presentation.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import pucaberta.pucminas.core.PermissionUtils
import pucaberta.pucminas.core.PermissionUtils.PermissionDeniedDialog.Companion.newInstance
import pucaberta.pucminas.core.PermissionUtils.isGPSEnabled
import pucaberta.pucminas.core.PermissionUtils.isPermissionGranted
import pucaberta.pucminas.core.observe
import pucaberta.pucminas.core.viewBinding
import pucaberta.pucminas.presentation.R
import pucaberta.pucminas.presentation.databinding.MapActivityBinding

class MapActivity : AppCompatActivity(),
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, OnMarkerClickListener,
    ActivityCompat.OnRequestPermissionsResultCallback {

    private var permissionDenied = false
    private lateinit var map: GoogleMap

    private val viewModel: MapViewModel by viewModel()

    private val binding: MapActivityBinding by viewBinding(
        MapActivityBinding::inflate
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        setupObservers()
    }

    private fun setupObservers() = with(viewModel) {
        observe(commonMarksObserver) {
            setupMarkers(it)
        }
        observe(iceiMarksObserver) {
            setupMarkers(it)
        }
    }

    private fun setupMarkers(markers: List<MarkerOptions>) {
        markers.forEach {
            map.addMarker(it)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap.apply {
            mapType = GoogleMap.MAP_TYPE_HYBRID
            isBuildingsEnabled = true
            isIndoorEnabled = true
            uiSettings.isZoomControlsEnabled = true
            setOnMyLocationButtonClickListener(this@MapActivity)
            setOnMyLocationClickListener(this@MapActivity)
            setOnInfoWindowClickListener {
                Log.d("Click", "Click")
            }
            setOnMarkerClickListener(this@MapActivity)
            moveCamera(CameraUpdateFactory.newCameraPosition(getCameraPosition(this)))
        }
        enableMyLocation()
        viewModel.loadCommonMarks()
        viewModel.loadICEIMarks()
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

    override fun onMarkerClick(marker: Marker): Boolean {
        Log.d("Click", marker.toString())
        return false
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

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }
    }

}