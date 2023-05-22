package repository


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import models.LocationType
import models.MarkLocation
import pucaberta.pucminas.core.providers.ResourceRawProvider
import pucaberta.pucminas.data.R
import repoInterfaces.MapRepository

class MapRepositoryImpl(
    private val rawProvider: ResourceRawProvider
) : MapRepository {

    override fun getAllCommonLocations(): List<MarkLocation> {
        return Gson().fromJson(
            rawProvider.getStringFromRaw(R.raw.communs_locations),
            TypeToken.getParameterized(List::class.java, MarkLocation::class.java).type
        )
    }


    override fun getIceiLocations(): List<MarkLocation> = listOf(
        MarkLocation(
            latitude = -19.923045,
            longitude = -43.994409,
            markTitle = "Instituto de Ciências Exatas e Informática - ICEI",
            locationType = LocationType.INSTITUTOS,
            id = 2,
            showQrCode = true,
        ),
        MarkLocation(
            latitude = -19.923527,
            longitude = -43.993354,
            markTitle = "Grupo J - 16h às 18h - Auditório Multiuso",
            locationType = LocationType.AUDITORIOS,
            id = 3,
            showQrCode = true
        )
    )
}