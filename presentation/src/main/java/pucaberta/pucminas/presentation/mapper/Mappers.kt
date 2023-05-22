package pucaberta.pucminas.presentation.mapper

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import models.LocationType
import models.MarkLocation
import pucaberta.pucminas.presentation.R

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
        icon(BitmapDescriptorFactory.fromResource(this@toMarkerOptions.locationType.getIconRes()))
    }
}

internal fun LocationType.getIconRes() = when (this) {
    LocationType.RECEPTIVO -> R.drawable.ic_receptivo
    LocationType.FEIRA_CURSOS -> R.drawable.ic_feira_de_cursos
    LocationType.INSTITUTOS -> R.drawable.ic_institutos_e_faculdades
    LocationType.AUDITORIOS -> R.drawable.ic_auditorios
}