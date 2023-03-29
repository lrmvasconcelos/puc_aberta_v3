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
            icon = R.drawable.ic_receptivo,
        ),
        MarkLocation(
            latitude = -19.922306,
            longitude = -43.993384,
            icon = R.drawable.ic_feira_de_cursos
        )
    )

    override fun getIceiLocations(): List<MarkLocation> = listOf(
        MarkLocation(
            latitude = -19.923045,
            longitude = -43.994409,
            markTitle = "Instituto de Ciências Exatas e Informática - ICEI",
            icon = R.drawable.ic_institutos_e_faculdades
        ),
        MarkLocation(
            latitude = -19.923527,
            longitude = -43.993354,
            markTitle = "Grupo J - 16h às 18h - Auditório Multiuso",
            icon = R.drawable.ic_auditorios
        )
    )
}