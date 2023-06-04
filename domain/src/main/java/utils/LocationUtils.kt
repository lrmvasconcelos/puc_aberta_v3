package utils

import android.location.Location

val RECEPTION_LOCATION = Location("Recepção").apply {
    latitude = -19.924542
    longitude = -43.993056
}

val FAIR_LOCATION = Location("Feira e Cursos").apply {
    latitude = -19.922306
    longitude = -43.993384
}

const val DISTANCE_FACTOR = 100