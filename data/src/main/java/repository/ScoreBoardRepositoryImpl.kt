package repository

import android.content.Context
import android.content.SharedPreferences
import pucaberta.pucminas.core.getPreferences
import repoInterfaces.ScoreBoardRepository

class ScoreBoardRepositoryImpl(
    private val context: Context
) : ScoreBoardRepository {

    private val sharedPreferences: SharedPreferences by lazy { context.getPreferences() }


    override fun saveLevel(level: Int) {
        if (level <= MAX_LEVEL) {
            sharedPreferences.edit().putInt(
                USER_LEVEL_CACHE_KEY, level
            ).apply()
        }
    }

    override fun getLevelInt(): Int {
        return sharedPreferences.getInt(USER_LEVEL_CACHE_KEY, 0)
    }

    companion object {
        private const val USER_LEVEL_CACHE_KEY = "puc_aberta_user_level_cache_key"
        private const val MAX_LEVEL = 3
    }
}