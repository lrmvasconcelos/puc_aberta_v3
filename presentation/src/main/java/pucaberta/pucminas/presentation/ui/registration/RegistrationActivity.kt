package pucaberta.pucminas.presentation.ui.registration

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.journeyapps.barcodescanner.ScanOptions
import models.BottomSheetTypeEnum
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pucaberta.pucminas.core.*
import pucaberta.pucminas.core.PermissionUtils.LOCATION_PERMISSION_REQUEST_CODE
import pucaberta.pucminas.core.PermissionUtils.PermissionDeniedDialog.Companion.newInstance
import pucaberta.pucminas.core.PermissionUtils.enableMyLocation
import pucaberta.pucminas.core.PermissionUtils.isGPSEnabled
import pucaberta.pucminas.core.PermissionUtils.isPermissionGranted
import pucaberta.pucminas.core.camera.QRCodeActivity
import pucaberta.pucminas.core.camera.setupQrCodeScanner
import pucaberta.pucminas.core.event.BottomSheetFinishEvent
import pucaberta.pucminas.presentation.R
import pucaberta.pucminas.presentation.databinding.RegistrationActivityBinding
import pucaberta.pucminas.presentation.ui.bottomsheet.RegisterBottomSheetDialog
import pucaberta.pucminas.presentation.ui.bottomsheet.SuccessBottomSheetDialog
import pucaberta.pucminas.presentation.ui.map.MapActivity
import utils.DISTANCE_FACTOR
import utils.RECEPTION_LOCATION

class RegistrationActivity : AppCompatActivity(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    private var permissionDenied = false

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var qrCodeScanner: ActivityResultLauncher<ScanOptions>

    private val viewModel: RegistrationViewModel by viewModel()

    private val finishEvent: BottomSheetFinishEvent by inject()

    private val binding: RegistrationActivityBinding by viewBinding(
        RegistrationActivityBinding::inflate
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupObservers()
        configQrScanner()
        setupClicks()
    }

    private fun setupObservers() {
        observeEvent(finishEvent.finishFlow) {
            when (it) {
                BottomSheetTypeEnum.QRCODE.name -> scanCode()
                BottomSheetTypeEnum.GEOLOCATION.name -> checkLocationRequirements()
                BottomSheetTypeEnum.SUCCESS.name -> openMapScreen()
            }
        }
    }

    private fun configQrScanner() {
        qrCodeScanner = setupQrCodeScanner {
            if (it.contents != null) {
                if (it.contents.equals(CONFIRMATION_KEY)) {
                    showWelcomeDialog()
                } else {
                    showQRCodeErrorDialog()
                }
            }
        }
    }

    private fun setupClicks() {
        binding.btnGeolocation.clickWithDebounce {
            openBottomSheet(
                R.string.geo_register_hint_description,
                BottomSheetTypeEnum.GEOLOCATION.name
            )
        }
        binding.btnQrCode.setOnClickListener {
            openBottomSheet(
                R.string.qr_code_register_hint_description,
                BottomSheetTypeEnum.QRCODE.name
            )
        }
    }

    private fun openBottomSheet(descriptionID: Int, lottieKey: String) {
        RegisterBottomSheetDialog.newInstance(descriptionID, lottieKey).showBottomSheet(
            this.supportFragmentManager,
            RegisterBottomSheetDialog::class.java.name
        )
    }

    private fun checkLocationRequirements() {
        if (isGPSEnabled(this)) {
            enableMyLocation(this) {
                getMyCurrentLocation()
            }
        } else {
            Toast.makeText(
                this, this.resources.getString(R.string.gps_disable_message), Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(
                requestCode, permissions, grantResults
            )
            return
        }

        if (isPermissionGranted(
                permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION
            ) || isPermissionGranted(
                permissions, grantResults, Manifest.permission.ACCESS_COARSE_LOCATION
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
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null && location.distanceTo(RECEPTION_LOCATION) <= DISTANCE_FACTOR) {
                showWelcomeDialog()
            } else {
                Log.d("Teste", "Error")
                //TODO() Modal de erro
            }
        }
    }

    private fun showMissingPermissionError() {
        newInstance(true).show(supportFragmentManager, "dialog")
    }

    private fun scanCode() {
        val options = ScanOptions().apply {
            setBeepEnabled(true)
            setOrientationLocked(true)
            captureActivity = QRCodeActivity::class.java
        }
        qrCodeScanner.launch(options)
    }

    private fun openMapScreen() {
        startWithAnimation(MapActivity.newInstance(this@RegistrationActivity).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        viewModel.setIsLogged()
    }

    private fun showWelcomeDialog() {
        viewModel.setIsLogged()
        SuccessBottomSheetDialog.newInstance().showBottomSheet(
            this.supportFragmentManager,
            SuccessBottomSheetDialog::class.java.name
        )
    }

    private fun showQRCodeErrorDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.register_screen_confirmation_error_title))
            setMessage(getString(R.string.register_screen_confirmation_error_desciption))
            setPositiveButton(
                "OK"
            ) { dialogInterface, i -> dialogInterface.dismiss() }.show()
        }
    }

    companion object {

        private const val CONFIRMATION_KEY = "PUC_ABERTA_2023"
        fun newInstance(context: Context): Intent {
            return Intent(context, RegistrationActivity::class.java)
        }
    }
}