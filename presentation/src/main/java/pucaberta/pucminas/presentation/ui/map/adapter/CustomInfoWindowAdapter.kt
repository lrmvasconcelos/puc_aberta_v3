package pucaberta.pucminas.presentation.ui.map.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import models.MarkLocation
import pucaberta.pucminas.presentation.R
import pucaberta.pucminas.presentation.databinding.CustomInfoContentsBinding

class CustomInfoWindowAdapter(
    context: Context,
    private val markers: List<MarkLocation>
) : GoogleMap.InfoWindowAdapter {

    private val binding: CustomInfoContentsBinding by lazy {
        CustomInfoContentsBinding.inflate(
            LayoutInflater.from(context)
        )
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View? {
        render(marker)
        return binding.root
    }

    private fun render(marker: Marker) {
        val badge = R.drawable.ic_qr_code

        val hasToShowQrCode =
            markers.firstOrNull { marker.zIndex == it.id.toFloat() }?.showQrCode == true

        if (hasToShowQrCode) {
            binding.badge.apply {
                isVisible = true
                setImageResource(badge)
            }
        }

        // Set the title and snippet for the custom info window
        binding.title.text = marker.title

        if (hasToShowQrCode) {
            binding.snippet.apply {
                text = "Qr Code Disponível"
                isVisible = true
            }
        }
    }
}