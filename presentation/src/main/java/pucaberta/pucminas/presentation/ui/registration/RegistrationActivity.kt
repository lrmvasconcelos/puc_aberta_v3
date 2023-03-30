package pucaberta.pucminas.presentation.ui.registration

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel
import pucaberta.pucminas.core.PermissionUtils.LOCATION_PERMISSION_REQUEST_CODE
import pucaberta.pucminas.core.PermissionUtils.PermissionDeniedDialog.Companion.newInstance
import pucaberta.pucminas.core.PermissionUtils.enableMyLocation
import pucaberta.pucminas.core.PermissionUtils.isGPSEnabled
import pucaberta.pucminas.core.PermissionUtils.isPermissionGranted
import pucaberta.pucminas.core.clickWithDebounce
import pucaberta.pucminas.core.viewBinding
import pucaberta.pucminas.presentation.R
import pucaberta.pucminas.presentation.databinding.RegistrationActivityBinding
import utils.DISTANCE_FACTOR
import utils.RECEPTION_LOCATION

class RegistrationActivity : AppCompatActivity(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    private var permissionDenied = false

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: RegistrationViewModel by viewModel()

    private val binding: RegistrationActivityBinding by viewBinding(
        RegistrationActivityBinding::inflate
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupClicks()
    }

    private fun setupClicks() {
        binding.btnGeolocation.clickWithDebounce {
            checkLocationRequirements()
        }
    }

    private fun checkLocationRequirements() {
        if (isGPSEnabled(this)) {
            enableMyLocation(this) {
                getMyCurrentLocation()
            }
        } else {
            Toast.makeText(
                this,
                this.resources.getString(R.string.gps_disable_message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
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
            getMyCurrentLocation()
        } else {
            //TODO() -> Mensagem erro permissão negada
            permissionDenied = true
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMyCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null && location.distanceTo(RECEPTION_LOCATION) <= DISTANCE_FACTOR) {
                    Log.d("Teste", "Sucesso")
                } else {
                    Log.d("Teste", "Error")
                    //TODO() Modal de erro
                }
            }
    }

    private fun showMissingPermissionError() {
        newInstance(true).show(supportFragmentManager, "dialog")
    }
}