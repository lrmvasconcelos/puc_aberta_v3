package repository


import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import models.MarkLocation
import pucaberta.pucminas.core.getPreferences
import pucaberta.pucminas.data.R
import repoInterfaces.MapRepository

class MapRepositoryImpl(
    private val context: Context
) : MapRepository {

    private val sharedPreferences: SharedPreferences by lazy { context.getPreferences() }

    override fun getCommonLocations(): List<MarkLocation> {

        val commonsLocation = sharedPreferences.getString(USER_LOCATION_CACHE_KEY, null)

        return Gson().fromJson(
            commonsLocation,
            TypeToken.getParameterized(List::class.java, MarkLocation::class.java).type
        )
    }

    override fun updateCommonLocations(markers: List<MarkLocation>) {

        val json = GsonBuilder().serializeNulls().create().toJson(markers)

        sharedPreferences.edit().putString(
            USER_LOCATION_CACHE_KEY, json
        ).apply()
    }

    override fun getIceiLocations(): List<MarkLocation> {

        val iceiLocation = sharedPreferences.getString(ICEI_LOCATION_CACHE_KEY, null)

        return Gson().fromJson(
            iceiLocation,
            TypeToken.getParameterized(List::class.java, MarkLocation::class.java).type
        )
    }

    override fun updateIceiLocations(markers: List<MarkLocation>) {
        val json = GsonBuilder().serializeNulls().create().toJson(markers)
        sharedPreferences.edit().putString(
            ICEI_LOCATION_CACHE_KEY, json
        ).apply()
    }

    override fun getAllLocations(): List<MarkLocation> {

        val iceiLocationList = getIceiLocations()

        val commonsLocationList = getCommonLocations()

        val locationList = commonsLocationList.toMutableList().apply {
            addAll(iceiLocationList)
        }.toList()

        return locationList
    }

    companion object {
        private const val ICEI_LOCATION_CACHE_KEY = "puc_aberta_location_cache_key"
        private const val USER_LOCATION_CACHE_KEY = "puc_aberta_user_location_cache_key"
    }
}