package pucaberta.pucminas.presentation.mapper

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import models.MarkLocation

internal fun List<MarkLocation>.toMarkerOptionsList(): List<MarkerOptions> {
    return this.map { it.toMarkerOptions() }
}

private fun MarkLocation.toMarkerOptions(): MarkerOptions {
    return MarkerOptions().apply {
        position(LatLng(latitude, longitude))
        markTitle?.let {
            title(it)
        }
        zIndex(id.toFloat())
        icon(BitmapDescriptorFactory.fromResource(this@toMarkerOptions.icon))
    }
}