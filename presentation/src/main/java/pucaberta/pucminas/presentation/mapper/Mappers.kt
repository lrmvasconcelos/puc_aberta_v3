package pucaberta.pucminas.presentation.mapper

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import models.IconType
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
        icon(BitmapDescriptorFactory.fromResource(this@toMarkerOptions.iconType.getIconRes()))
    }
}

internal fun IconType.getIconRes() = when (this) {
    IconType.RECEPTIVO -> R.drawable.ic_receptivo
    IconType.FEIRA_CURSOS -> R.drawable.ic_feira_de_cursos
    IconType.INSTITUTOS -> R.drawable.ic_institutos_e_faculdades
    IconType.AUDITORIOS -> R.drawable.ic_auditorios
}