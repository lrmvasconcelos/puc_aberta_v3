package pucaberta.pucminas.presentation.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.journeyapps.barcodescanner.ScanOptions
import models.BottomSheetTypeEnum
import models.MarkLocation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pucaberta.pucminas.core.*
import pucaberta.pucminas.core.PermissionUtils.PermissionDeniedDialog.Companion.newInstance
import pucaberta.pucminas.core.PermissionUtils.isGPSEnabled
import pucaberta.pucminas.core.PermissionUtils.isPermissionGranted
import pucaberta.pucminas.core.camera.QRCodeActivity
import pucaberta.pucminas.core.camera.setupQrCodeScanner
import pucaberta.pucminas.core.event.BottomSheetFinishEvent
import pucaberta.pucminas.presentation.R
import pucaberta.pucminas.presentation.databinding.MapActivityBinding
import pucaberta.pucminas.presentation.mapper.toMarkerOptionsList
import pucaberta.pucminas.presentation.ui.bottomsheet.GiftBottomSheetDialog
import pucaberta.pucminas.presentation.ui.bottomsheet.ProgrammingBottomSheetDialog
import pucaberta.pucminas.presentation.ui.bottomsheet.RegisterBottomSheetDialog
import pucaberta.pucminas.presentation.ui.map.adapter.CustomInfoWindowAdapter

class MapActivity : AppCompatActivity(), GoogleMap.OnMyLocationButtonClickListener,
    OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private var permissionDenied = false
    private lateinit var map: GoogleMap

    private val viewModel: MapViewModel by viewModel()

    private val binding: MapActivityBinding by viewBinding(
        MapActivityBinding::inflate
    )

    private val finishEvent: BottomSheetFinishEvent by inject()

    private lateinit var qrCodeScanner: ActivityResultLauncher<ScanOptions>

    private val shakeAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(this@MapActivity, R.anim.shake)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide();
        }
        setContentView(binding.root)
        setupComponents()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        setupObservers()
        viewModel.getUserScore()
    }

    private fun setupComponents() {
        with(binding) {
            scoreSeekBar.isTouchListenerEnabled = false
            clButton.clickWithDebounce {
                viewModel.processButtonClick()
            }
            actvEvent.clickWithDebounce {
                openProgrammingBottomSheet()
            }
            actvPuc.clickWithDebounce {
                openPucMinasWebsite()
            }
            cardVR.clickWithDebounce {
                openVirtualTour()
            }
        }
        configQrScanner()
    }

    private fun configQrScanner() {
        qrCodeScanner = setupQrCodeScanner {
            if (it.contents != null) {
                viewModel.processQrCodeResult(it.contents)
            }
        }
    }

    private fun animateComponents() = with(binding) {
        actvScore.startAnimation(shakeAnimation)
    }

    private fun scanCode() {
        val options = ScanOptions().apply {
            setBeepEnabled(true)
            setOrientationLocked(true)
            captureActivity = QRCodeActivity::class.java
        }
        qrCodeScanner.launch(options)
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

        observe(successState) {
            showSuccessBottomSheet()
        }

        observeEvent(finishEvent.finishFlow) {
            when (it) {
                BottomSheetTypeEnum.GIFT.name -> {
                    moveCameraToFair()
                }
                BottomSheetTypeEnum.QRCODE.name -> scanCode()
            }
        }
    }

    private fun setupMarkers(markers: List<MarkLocation>) {
        map.clear()
        map.setInfoWindowAdapter(
            CustomInfoWindowAdapter(
                context = this@MapActivity,
                markers = markers,
                isChallangeComplete = viewModel.isChallengeCompleted
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
            setOnInfoWindowClickListener {
                viewModel.onMarkerClick()
            }
            moveCamera(CameraUpdateFactory.newCameraPosition(getCameraPosition(this)))
        }
//        enableMyLocation()
        viewModel.loadAllMarks()
    }

    private fun getCameraPosition(googleMap: GoogleMap) =
        CameraPosition.builder(googleMap.cameraPosition).target(viewModel.reception)
            .bearing(310.toFloat()).zoom(17.toFloat()).build()

    override fun onMyLocationButtonClick(): Boolean {
        if (!isGPSEnabled(this)) {
            Toast.makeText(
                this, this.resources.getString(R.string.gps_disable_message), Toast.LENGTH_SHORT
            ).show()
        }
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
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode != PermissionUtils.LOCATION_PERMISSION_REQUEST_CODE) {
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
        RegisterBottomSheetDialog.newInstance(
            R.string.qr_code_hint_description, BottomSheetTypeEnum.QRCODE.name
        ).showBottomSheet(
            this.supportFragmentManager, RegisterBottomSheetDialog::class.java.name
        )
    }

    private fun setUserLevel(level: Int) {
        with(binding) {
            actvScore.text = level.toString()
            scoreSeekBar.currentValue = level
            if (viewModel.isAnimationEnabled) {
                animateComponents()
            }
        }
    }

    private fun showSuccessBottomSheet() {
        GiftBottomSheetDialog.newInstance()
            .showBottomSheet(this.supportFragmentManager, GiftBottomSheetDialog::class.java.name)
        changeLottieAnimation()
    }

    private fun changeLottieAnimation() = with(binding) {
        lottieGift.setAnimation(R.raw.giftbox)
        animationView.isVisible = true
    }

    private fun moveCameraToFair() {
        map.moveCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.builder(map.cameraPosition).target(viewModel.fair)
                    .bearing(310.toFloat()).zoom(18.toFloat()).build()
            )
        )
    }

    private fun openProgrammingBottomSheet() {
        ProgrammingBottomSheetDialog.newInstance()
            .showBottomSheet(
                this.supportFragmentManager,
                ProgrammingBottomSheetDialog::class.java.name
            )
    }

    private fun openPucMinasWebsite() {
        val uri =
            Uri.parse("https://www.pucminas.br/MundoPUCMinas/Paginas/Instituto.aspx?institutoID=0d2d7e4db-c442-4db0-89d0-09349063b067")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        if (intent.resolveActivity(packageManager) == null) {
            intent.data =
                Uri.parse("https://www.pucminas.br/MundoPUCMinas/Paginas/Instituto.aspx?institutoID=0d2d7e4db-c442-4db0-89d0-09349063b067")
        }
        this.startActivity(intent)
    }

    private fun openVirtualTour() {
        val uri =
            Uri.parse("https://kuula.co/share/Np5cC/collection/7kDK4?logo=1&info=0&logosize=118&fs=1&vr=1&sd=1&thumbs=1&margin=18&inst=pt&keys=0")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        if (intent.resolveActivity(packageManager) == null) {
            intent.data =
                Uri.parse("https://kuula.co/share/Np5cC/collection/7kDK4?logo=1&info=0&logosize=118&fs=1&vr=1&sd=1&thumbs=1&margin=18&inst=pt&keys=0")
        }
        this.startActivity(intent)
    }

    companion object {

        fun newInstance(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }
    }

}