package repository


import models.MarkLocation
import pucaberta.pucminas.data.R
import repoInterfaces.MapRepository

class MapRepositoryImpl: MapRepository {

    override fun getAllCommonLocations(): List<MarkLocation> = listOf(
        MarkLocation(
            latitude = -19.924542,
            longitude = -43.993056,
            markTitle = "Receptivo",
            icon = R.drawable.ic_receptivo
        ),
        MarkLocation(
            latitude = -19.922306,
            longitude = -43.993384,
            icon = R.drawable.ic_feira_de_cursos
        )
    )
}