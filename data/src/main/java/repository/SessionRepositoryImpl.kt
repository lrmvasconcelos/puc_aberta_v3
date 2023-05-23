package repository

import android.content.Context
import android.content.SharedPreferences
import pucaberta.pucminas.core.getPreferences
import pucaberta.pucminas.core.providers.ResourceRawProvider
import pucaberta.pucminas.data.R
import repoInterfaces.SessionRepository

class SessionRepositoryImpl(
    private val context: Context,
    private val rawProvider: ResourceRawProvider
) : SessionRepository {

    private val sharedPreferences: SharedPreferences by lazy { context.getPreferences() }

    override fun isLogged(): Boolean {
        return sharedPreferences.getBoolean(
            CACHE_KEY, false
        )
    }

    override fun setLogged() {
        sharedPreferences.edit().putBoolean(
            CACHE_KEY, true
        ).apply()
    }

    override fun validateLocationsAtCache() {
        val iceiLocation = sharedPreferences.getString(ICEI_LOCATION_CACHE_KEY, null)

        if (iceiLocation == null) {
            saveIceiLocationAtCache()
        }

        val commonsLocation = sharedPreferences.getString(USER_LOCATION_CACHE_KEY, null)

        if (commonsLocation == null) {
            saveCommonsLocationAtCache()
        }
    }

    private fun saveIceiLocationAtCache() {
        sharedPreferences.edit().putString(
            ICEI_LOCATION_CACHE_KEY, rawProvider.getStringFromRaw(R.raw.icei_locations)
        ).apply()
    }

    private fun saveCommonsLocationAtCache() {
        sharedPreferences.edit().putString(
            USER_LOCATION_CACHE_KEY, rawProvider.getStringFromRaw(R.raw.commons_locations)
        ).apply()
    }

    companion object {
        private const val CACHE_KEY = "puc_aberta_cache_key"
        private const val ICEI_LOCATION_CACHE_KEY = "puc_aberta_location_cache_key"
        private const val USER_LOCATION_CACHE_KEY = "puc_aberta_user_location_cache_key"
    }
}