package repository


import models.IconType
import models.MarkLocation
import pucaberta.pucminas.data.R
import repoInterfaces.MapRepository

class MapRepositoryImpl: MapRepository {

    override fun getAllCommonLocations(): List<MarkLocation> = listOf(
        MarkLocation(
            latitude = -19.924542,
            longitude = -43.993056,
            markTitle = "Receptivo",
            iconType = IconType.RECEPTIVO,
            id = 0
        ),
        MarkLocation(
            latitude = -19.922306,
            longitude = -43.993384,
            iconType = IconType.FEIRA_CURSOS,
            id = 1,
            showQrCode = true
        )
    )

    override fun getIceiLocations(): List<MarkLocation> = listOf(
        MarkLocation(
            latitude = -19.923045,
            longitude = -43.994409,
            markTitle = "Instituto de Ciências Exatas e Informática - ICEI",
            iconType = IconType.INSTITUTOS,
            id = 2,
            showQrCode = true,
        ),
        MarkLocation(
            latitude = -19.923527,
            longitude = -43.993354,
            markTitle = "Grupo J - 16h às 18h - Auditório Multiuso",
            iconType = IconType.AUDITORIOS,
            id = 3,
            showQrCode = true
        )
    )
}