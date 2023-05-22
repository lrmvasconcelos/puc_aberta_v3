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

    override fun getIceiLocations(): List<MarkLocation> {
        return Gson().fromJson(
            rawProvider.getStringFromRaw(R.raw.icei_locations),
            TypeToken.getParameterized(List::class.java, MarkLocation::class.java).type
        )
    }
}