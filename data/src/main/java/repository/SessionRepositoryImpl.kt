package repository

import android.content.Context
import android.content.SharedPreferences
import pucaberta.pucminas.core.getPreferences
import repoInterfaces.SessionRepository

class SessionRepositoryImpl(
    private val context: Context
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

    companion object {
        private const val CACHE_KEY = "puc_aberta_cache_key"
    }
}