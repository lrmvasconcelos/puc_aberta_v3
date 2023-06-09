package pucaberta.pucminas.presentation.ui.map.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import models.LocationType
import models.MarkLocation
import pucaberta.pucminas.presentation.R
import pucaberta.pucminas.presentation.databinding.CustomInfoContentsBinding
import pucaberta.pucminas.presentation.databinding.GiftInfoContentsBinding

class CustomInfoWindowAdapter(
    context: Context,
    private val markers: List<MarkLocation>,
    private val isChallangeComplete: Boolean
) : GoogleMap.InfoWindowAdapter {

    private val binding: CustomInfoContentsBinding by lazy {
        CustomInfoContentsBinding.inflate(
            LayoutInflater.from(context)
        )
    }

    private val bindingGift: GiftInfoContentsBinding by lazy {
        GiftInfoContentsBinding.inflate(
            LayoutInflater.from(context)
        )
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View? {
        val tempMarker = markers.firstOrNull { marker.zIndex == it.id.toFloat() }
        return when {
            tempMarker?.locationType == LocationType.RECEPTIVO || tempMarker?.locationType == LocationType.CANTINA -> null
            tempMarker?.locationType == LocationType.FEIRA_CURSOS && isChallangeComplete -> {
                bindingGift.root
            }
            else -> {
                render(marker)
                binding.root
            }
        }
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
        markers.find { marker.zIndex == it.id.toFloat() }?.markTitle?.let {
            binding.title.isVisible = true
            binding.title.text = it
        }

        if (hasToShowQrCode) {
            binding.snippet.apply {
                text = context.getString(R.string.qr_code_available_description)
                isVisible = true
            }
        }
    }
}